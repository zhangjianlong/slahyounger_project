package com.slash.youth.data.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.slash.youth.data.Global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by acer on 2017/3/29.
 */

public class SpUtil {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "slash_sp.config";


    public static void write(String k, int v) {
        write(FILE_NAME, k, v);
    }

    public static void write(String k, boolean v) {
        write(FILE_NAME, k, v);
    }

    public static void write(String k, long v) {
        write(FILE_NAME, k, v);
    }

    public static void write(String k, String v) {
        write(FILE_NAME, k, v);
    }

    public static void write(String k, Serializable v) {
        saveObject(v, k);
    }


    public static int readInt(String k, int defv) {
        return readInt(FILE_NAME, k, defv);
    }


    public static long readLong(String k, long defv) {
        return readLong(FILE_NAME, k, defv);
    }

    public static boolean readBoolean(String k, boolean defBool) {
        return readBoolean(FILE_NAME, k, defBool);
    }

    public static String readString(String k, String defV) {
        return readString(FILE_NAME, k, defV);
    }


    public static void remove(Context context, String k) {
        remove(FILE_NAME, k);
    }

    public static void clean() {
        clean(FILE_NAME);
    }

    public static void remove(String... k) {
        SharedPreferences preference = Global.application.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        for (String key : k) {
            editor.remove(key);
        }
        editor.commit();
    }


    public static void write(String fileName, String k, int v) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(k, v);
        editor.commit();
    }

    public static void write(String fileName, String k,
                             boolean v) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(k, v);
        editor.commit();
    }


    public static void write(String fileName, String k,
                             long v) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putLong(k, v);
        editor.commit();
    }

    public static void write(String fileName, String k,
                             String v) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(k, v);
        editor.commit();
    }


    public static int readInt(String fileName, String k,
                              int defv) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        return preference.getInt(k, defv);
    }

    public static long readLong(String fileName, String k,
                                long defv) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        return preference.getLong(k, defv);
    }

    public static boolean readBoolean(String fileName,
                                      String k, boolean defBool) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        return preference.getBoolean(k, defBool);
    }

    public static String readString(String fileName, String k,
                                    String defV) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Global.application.MODE_PRIVATE);
        return preference.getString(k, defV);
    }

    public static void remove(String fileName, String k) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(k);
        editor.commit();
    }

    public static void clean(String fileName) {
        SharedPreferences preference = Global.application.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param key
     */
    public static boolean saveObject(Serializable ser,
                                     String key) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = Global.application.openFileOutput(key, Global.application.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public static void deleteObject(String key) {
        String filePath = Global.application.getFilesDir().getPath() + "/" + key;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 读取对象
     *
     * @param key
     * @return
     */
    public static Serializable readObject(String key) {
        if (!isExistDataCache(key))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = Global.application.openFileInput(key);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = Global.application.getFileStreamPath(key);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return
     */
    public static boolean isExistDataCache(String key) {
        if (Global.application == null)
            return false;
        boolean exist = false;
        File data = Global.application.getFileStreamPath(key);
        if (data.exists())
            exist = true;
        return exist;
    }
}
