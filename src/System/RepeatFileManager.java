package System;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;


public class RepeatFileManager {

    private static final String EMPTY_FILE = "empty_file"; //空文件


    private Map<Long, String> mFileSizeMap = new HashMap<>(); //保存文件大小
    private Map<String, String> mFileHashMap = new HashMap<>(); //保存文件hash
    private Map<String, List<String>> mRepeatFileMap = new HashMap<>(); //最终重复文件
    private boolean mCanceled;
    private OnRepeatStatsListener onRepeatStatsListener;

    public void start(final File dir) {
        new Thread() {
            public void run() {
                mCanceled = false;
                mFileSizeMap.clear();
                mFileHashMap.clear();
                mRepeatFileMap.clear();
                calc(dir);
                if (onRepeatStatsListener != null) {
                    onRepeatStatsListener.onRepeatStatsFinished(mRepeatFileMap);
                }
            };
        }.start();
    }

    private void calc(File dir) {
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    calc(file);
                    continue;
                }
                if (file.length() == 0) {
                    //空文件
                    List<String> pathList = mRepeatFileMap.get(EMPTY_FILE);
                    if (pathList == null) {
                        pathList = new ArrayList<>();
                        mRepeatFileMap.put(EMPTY_FILE, pathList);
                    }
                    pathList.add(file.getAbsolutePath());
                } else {
                    if(mFileSizeMap.containsKey(file.length())){
                        //大小重复，计算文件散列
                        String path = mFileSizeMap.get(file.length());
                        if (!mFileHashMap.values().contains(path)) {
                            String firstHash = calcFileKey(new File(path));
                            if (firstHash != null) {
                                mFileHashMap.put(firstHash, path);
                            }
                        }
                        String hash = calcFileKey(file);
                        if (hash != null) {
                            if (mFileHashMap.containsKey(hash)) {
                                //散列重复，保存文件路径
                                List<String> pathList = mRepeatFileMap.get(hash);
                                if (pathList == null) {
                                    pathList = new ArrayList<>();
                                    pathList.add(mFileHashMap.get(hash));
                                    mRepeatFileMap.put(hash, pathList);
                                }
                                pathList.add(file.getAbsolutePath());
                            } else {
                                mFileHashMap.put(hash, file.getAbsolutePath());
                            }
                        }
                    }else {
                        mFileSizeMap.put(file.length(), file.getAbsolutePath());
                    }
                }
                if (mCanceled) {
                    break;
                }
            }
        }
    }

    /**
     * 计算文件唯一散列值
     * @param file
     * @return
     */
    private static String calcFileKey(File file) {
        String md5 = calcFileMd5(file);
        String crc32 = calcFileCRC32(file);
        if (!md5.isEmpty() && !crc32.isEmpty()) {
            return md5 + "_" + crc32;
        } else {
            return null;
        }
    }

    /**
     * 获取文件md5
     *
     * @param file
     * @return
     */
    private static String calcFileMd5(File file) {
        if (!file.isFile()) {
            return "";
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
            return byteToHex(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String byteToHex(byte[] data) {
        if (data != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                int v = data[i] & 0xFF;
                builder.append(String.format("%02x", v));
            }
            return builder.toString();
        } else {
            return null;
        }
    }

    /**
     * 获取文件crc32
     * @param file
     * @return
     */
    private static String calcFileCRC32(File file) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            CRC32 crc = new CRC32();
            byte[] bytes = new byte[1024];
            int cnt;
            while ((cnt = in.read(bytes)) != -1) {
                crc.update(bytes, 0, cnt);
            }
            return Long.toHexString(crc.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void testCalc(File file) {
        System.out.println(calcFileMd5(file));
    }

    public Map<String, List<String>> getRepeatFiles() {
        return mRepeatFileMap;
    }

    public void setOnRepeatStatsListener(
            OnRepeatStatsListener onRepeatStatsListener) {
        this.onRepeatStatsListener = onRepeatStatsListener;
    }

    public interface OnRepeatStatsListener {
        void onRepeatStatsFinished(Map<String, List<String>> repeatFiles);
    }
}

