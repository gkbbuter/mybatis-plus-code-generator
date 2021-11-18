package cn.com.bbut.maven.plugin.util;

import cn.com.bbut.maven.plugin.enums.FileMode;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author guok
 * @date: 2020/12/3
 */
public class Utils {

    /**
     * 尝试获取文件绝对路径 1. 已经是绝对路径，返回对应path 2. 当前路径，返回当前路径对应path
     *
     * @param file
     *            文件名(绝对或相对路径)
     * @param mode
     *            读写模式
     * @param isDirectory
     *            是否目录
     * @return 绝对路径
     */
    public static File getAbsoluteFile(String file, FileMode mode, boolean isDirectory)
            throws FileNotFoundException {
        // 尝试全路径读取
        File fullPathFile = Paths.get(file).toAbsolutePath().toFile();
        if (isDirectory) {
            if (!fullPathFile.isDirectory()) {
                throw new FileNotFoundException(file + " is not a dirctory");
            }
        } else {
            if (fullPathFile.isDirectory()) {
                throw new FileNotFoundException(file + " is not a file but a dirctory");
            }
        }

        if (canOpen(fullPathFile, mode)) {
            // 路径正确，可打开，直接返回
            return fullPathFile;
        } else {
            // 不可打开，参数路径非绝对路径，尝试从当前路径获取
            Path path = Paths.get("", file).toAbsolutePath();
            fullPathFile = path.toFile();
            if (canOpen(fullPathFile, mode)) {
                return fullPathFile;
            } else {
                throw new FileNotFoundException(file + "isn't exist or can't open");
            }
        }
    }

    /**
     * 判断文件是否可读/写
     *
     * @param file
     *            文件绝对路径
     * @param mode
     *            读写模式
     * @return true 可读/写 false 不可读/写
     */
    private static boolean canOpen(File file, FileMode mode) {
        boolean canOpen;
        if (mode == FileMode.READ) {
            canOpen = file.canRead();
        } else {
            // 如果是文件，需要判断所在目录是否可写
            if (!file.isDirectory()) {
                File parent = file.getParentFile();
                canOpen = parent == null ? false : parent.canWrite();
            } else {
                canOpen = file.canWrite();
            }
        }
        return canOpen;
    }

    /**
     * 获取绝对路径
     *
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        return Paths.get(path).toAbsolutePath().toString();
    }

    public static boolean fileExists(String path) throws FileNotFoundException {
        if (StringUtils.isBlank(path)) {
            throw new FileNotFoundException("File does not exist, path=" + path);
        }
        File file = new File(path);
        return file.exists();
    }
}
