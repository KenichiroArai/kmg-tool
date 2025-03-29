package kmg.tool.application.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.types.KmgDelimiterTypes;
import kmg.fund.infrastructure.context.KmgMessageSource;
import kmg.tool.application.logic.JavadocAppenderLogic;
import kmg.tool.application.service.JavadocAppenderService;
import kmg.tool.domain.types.KmgToolLogMessageTypes;
import kmg.tool.infrastructure.exception.KmgToolException;

/**
 * Javadoc追加サービス<br>
 *
 * @author KenichiroArai
 */
@Service
public class JavadocAppenderServiceImpl implements JavadocAppenderService {

    /**
     * ロガー
     *
     * @since 0.1.0
     */
    private final Logger logger;

    /**
     * KMGメッセージリソース
     *
     * @since 0.1.0
     */
    @Autowired
    private KmgMessageSource messageSource;

    /** Javadoc追加ロジック */
    @Autowired
    private JavadocAppenderLogic javadocAppenderLogic;

    /** 対象ファイルパス */
    private Path targetPath;

    /** テンプレートファイルパス */
    private Path templatePath;

    /**
     * 新しいJavaファイルを返す。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param javaFile
     *                           Javaファイル
     * @param fileContentBuilder
     *                           ファイル内容ビルダー
     * @param tagMap
     *                           タグマップ
     * @param insertAtTop
     *                           タグを先頭に挿入するかどうか
     *
     * @throws IOException
     *                     入出力例外
     *
     * @return ファイル内容
     */
    private static String getNewJavaFile(final Path javaFile, final StringBuilder fileContentBuilder,
        final Map<String, String> tagMap, final boolean insertAtTop) throws IOException {

        try (BufferedReader br = Files.newBufferedReader(javaFile)) {

            /* 行ごとの読み込み */
            boolean             isInJavadoc    = false;
            String              line           = null;
            final StringBuilder javadocBuilder = new StringBuilder();
            final StringBuilder contentBuilder = new StringBuilder();
            boolean             foundFirstTag  = false;

            while ((line = br.readLine()) != null) {

                final String trimmedLine = line.trim();

                if (trimmedLine.startsWith("/**")) {

                    /* Javadocの開始 */

                    isInJavadoc = true;
                    javadocBuilder.setLength(0);
                    contentBuilder.setLength(0);
                    foundFirstTag = false;

                    if (!trimmedLine.endsWith("*/")) {

                        // Javadocの開始行の末尾に*/がない（複数行）場合
                        javadocBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                        continue;

                    }

                    // 1行Javadocの場合の処理
                    fileContentBuilder.append("/**").append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                    // コメントを取得して出力
                    final String comment = trimmedLine.substring(3, trimmedLine.length() - 2).trim();

                    if (!comment.isEmpty()) {

                        fileContentBuilder.append(" * ").append(comment).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                    }

                    // タグを出力
                    JavadocAppenderServiceImpl.processJavadocTags(fileContentBuilder, comment, tagMap);

                    fileContentBuilder.append(" */").append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                    isInJavadoc = false;
                    continue;

                }

                if (isInJavadoc && trimmedLine.endsWith("*/")) {

                    /* Javadocの終了 */

                    isInJavadoc = false;

                    /* 既存のJavadocの内容を解析してタグを置き換え */
                    final String[] javadocLines
                        = javadocBuilder.toString().split(KmgDelimiterTypes.LINE_SEPARATOR.get());

                    /* 各行を処理 */
                    for (final String javadocLine : javadocLines) {

                        final String trimmedJavadocLine = javadocLine.trim();

                        // 最初のタグを検出した位置でtagMapのタグを挿入（insertAtTopがtrueの場合）
                        if (insertAtTop && !foundFirstTag && trimmedJavadocLine.contains("@")) {

                            foundFirstTag = true;

                            /* tagMapのタグを出力 */
                            for (final Map.Entry<String, String> entry : tagMap.entrySet()) {

                                fileContentBuilder.append(" * ").append(entry.getKey()).append(" ")
                                    .append(entry.getValue()).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                            }

                        }

                        if (!trimmedJavadocLine.contains("@")) {

                            // タグ行でない場合は保持
                            fileContentBuilder.append(javadocLine).append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                            continue;

                        }

                        // タグ行の場合、tagMapに存在するタグかチェック
                        boolean isTagInMap = false;

                        for (final String tag : tagMap.keySet()) {

                            if (trimmedJavadocLine.contains(tag)) {

                                isTagInMap = true;
                                break;

                            }

                        }

                        // tagMapにないタグ行は保持
                        if (!isTagInMap) {

                            fileContentBuilder.append(javadocLine).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                        }

                    }

                    // タグが見つからなかった場合や、insertAtTopがfalseの場合は末尾にタグを挿入
                    if (!foundFirstTag || !insertAtTop) {

                        /* tagMapのタグを出力 */
                        for (final Map.Entry<String, String> entry : tagMap.entrySet()) {

                            fileContentBuilder.append(" * ").append(entry.getKey()).append(" ").append(entry.getValue())
                                .append(KmgDelimiterTypes.LINE_SEPARATOR.get());

                        }

                    }

                    fileContentBuilder.append(" */").append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                    continue;

                }

                /* Javadoc内の行の処理 */
                if (!isInJavadoc) {

                    fileContentBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                    continue;

                }

                // Javadoc内の場合
                javadocBuilder.append(line).append(KmgDelimiterTypes.LINE_SEPARATOR.get());

            }

        }

        final String result = fileContentBuilder.toString();
        return result;

    }

    /**
     * Javadocのタグを処理する。<br>
     *
     * @author KenichiroArai
     *
     * @since 0.1.0
     *
     * @version 0.1.0
     *
     * @param fileContentBuilder
     *                           ファイル内容ビルダー
     * @param content
     *                           内容
     * @param tagMap
     *                           タグマップ
     */
    private static void processJavadocTags(final StringBuilder fileContentBuilder, final String content,
        final Map<String, String> tagMap) {

        // 既存のタグを確認
        final Map<String, Boolean> processedTags = new HashMap<>();

        // 既存のタグを処理
        for (final String tag : tagMap.keySet()) {

            if (content.contains(tag)) {

                fileContentBuilder.append(" * ").append(tag).append(" ").append(tagMap.get(tag))
                    .append(KmgDelimiterTypes.LINE_SEPARATOR.get());
                processedTags.put(tag, true);

            }

        }

        // 未処理のタグを追加
        for (final Map.Entry<String, String> entry : tagMap.entrySet()) {

            if (!processedTags.containsKey(entry.getKey())) {

                fileContentBuilder.append(" * ").append(entry.getKey()).append(" ").append(entry.getValue())
                    .append(KmgDelimiterTypes.LINE_SEPARATOR.get());

            }

        }

    }

    /**
     * 標準ロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     */
    public JavadocAppenderServiceImpl() {

        this(LoggerFactory.getLogger(JavadocAppenderServiceImpl.class));

    }

    /**
     * カスタムロガーを使用して入出力ツールを初期化するコンストラクタ<br>
     *
     * @since 0.1.0
     *
     * @param logger
     *               ロガー
     */
    protected JavadocAppenderServiceImpl(final Logger logger) {

        this.logger = logger;

    }

    /**
     * 初期化する
     *
     * @return true：成功、false：失敗
     *
     * @param targetPath
     *                     対象ファイルパス
     * @param templatePath
     *                     テンプレートファイルパス
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @SuppressWarnings("hiding")
    @Override
    public boolean initialize(final Path targetPath, final Path templatePath) throws KmgToolException {

        boolean result = false;

        this.targetPath = targetPath;
        this.templatePath = templatePath;

        /* Javadoc追加ロジックの初期化 */
        this.javadocAppenderLogic.initialize(targetPath, templatePath);

        result = true;
        return result;

    }

    /**
     * 処理する
     *
     * @return true：成功、false：失敗
     *
     * @throws KmgToolException
     *                          KMGツール例外
     */
    @Override
    public boolean process() throws KmgToolException {

        boolean result = false;

        /* タグマップの取得 */
        Map<String, String> tagMap;

        try {

            tagMap = this.javadocAppenderLogic.getTagMap();

        } catch (final KmgToolException e) {

            // TODO KenichiroArai 2025/03/29 メッセージ

            final KmgToolLogMessageTypes logMsgTypes = KmgToolLogMessageTypes.KMGTOOL_LOG31003;
            final Object[]               logMsgArgs  = {};
            final String                 logMsg      = this.messageSource.getLogMessage(logMsgTypes, logMsgArgs);
            this.logger.error(logMsg, e);

            throw e;

        }
        System.out.println(tagMap.toString());

        /* 対象のJavaファイルを取得 */
        final List<Path> javaFileList;

        int fileCount = 0;

        try (final Stream<Path> streamPath = Files.walk(this.targetPath)) {

            javaFileList = streamPath.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java"))
                .collect(Collectors.toList());

            fileCount += javaFileList.size();

        } catch (final IOException e) {

            // TODO KenichiroArai 2025/03/27 例外処理
            e.printStackTrace();
            return result;

        }

        /* 対象のJavaファイルをすべて読み込む */

        int lineCount = 0;

        for (final Path javaFile : javaFileList) {

            final StringBuilder fileContentBuilder = new StringBuilder();
            String              fileContent;

            try {

                fileContent = JavadocAppenderServiceImpl.getNewJavaFile(javaFile, fileContentBuilder, tagMap, true);

            } catch (final IOException e) {

                // TODO KenichiroArai 2025/03/27 例外処理
                e.printStackTrace();
                return result;

            }

            lineCount += KmgDelimiterTypes.LINE_SEPARATOR.split(fileContent).length;

            /* 修正した内容をファイルに書き込む */
            try {

                Files.writeString(javaFile, fileContent);

            } catch (final IOException e) {

                System.err.println("Error writing to file: " + javaFile);
                e.printStackTrace();
                result = false;

            }

        }

        System.out.println(String.format("fileCount: %d", fileCount));
        System.out.println(String.format("lineCount: %d", lineCount));

        return result;

    }

}
