package asia.ienter.qrscaner.models;

import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import asia.ienter.qrscaner.utils.Config;


/**
 * Created by phamquangmanh on 10/25/16.
 */
public class QRData {

    static AssetManager am;
    public static ArrayList<HistoryClass> historyList;
    public static QRData qrData;
    public static void CopyFile(InputStream in, OutputStream out) {
        byte[] buffer = new byte[1024];
        int read;
        try {
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("QRData", "Create Babybean.App False 3" + e.getMessage());
        }
    }

    public static void DeleteFile(String FilePath) {
        File file_delete = new File(FilePath);
        file_delete.delete();
    }

    public static boolean FileExit(String FilePath) {
        File file_check = new File(FilePath);

        return file_check.exists();
    }

//    public static void CopyFromAssets(String path) {
//        String[] files = null;
//        try {
//            am = MainActivity.Main.getAssets();
//            files = am.list(path);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return;
//        }
//
//        String totalPath = "";
//        InputStream in =null;
//        OutputStream out =null;
//
//        for (String name : files) {
//            totalPath = path + File.separator + name;
//
//
//            try {
//                in = am.open(totalPath);
//                Log.d("BB Copy File", "Yeah , " + totalPath + " is a file");
//
//                File dir = new File(Config.AppFolder + File.separator + path);
//                if (!dir.exists())
//                    dir.mkdirs();
//
//                Log.d("BB Copy File", "Creating output file at " + dir.getAbsolutePath());
//
//                File out_file = new File(dir.getAbsolutePath(), name);
//
//                if(!out_file.exists())
//                {
//                    out = new FileOutputStream(out_file);
//                    CopyFile(in, out);
//                    out.flush();
//                    in.close();
//                    out.close();
//                }else
//                {
//
//                    if(!MD5FileCheckFile("file:///android_asset/"+totalPath,dir+name))
//                    {
//                        out = new FileOutputStream(out_file);
//                        CopyFile(in, out);
//                        out.flush();
//                        in.close();
//                        out.close();
//                    }
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//
//                Log.d("BB Copy File", "Yeah , " + totalPath + " is a folder. So recurse");
//                CopyFromAssets(totalPath);
//            } finally {
//                in = null;
//                out = null;
//            }
//
//        }
//    }

    public static void CreateFolder(String Path) {
        File folder = new File(Path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        Log.d("QRData","Dzo 1.5");
    }

    public static void CreateRootFolder() {
        Log.d("QRData","Dzo");
        CreateFolder(Config.RootFolder);
        Log.d("QRData","Dzo 2");
        CreateFile(Config.RootFolder+"history.isa");
        Log.d("QRData","Dzo 3");

    }

    public static String CreateFile(String path){
        File  logFile2 = new File(path);
        String result = "";
        if (!logFile2.exists())
        {
            try {
                logFile2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            result = readFile(path);
        }
        return result;
    }

    public static boolean MD5FileCheckFile(String PathFile, String PathFile2) {

        File FilePath = new File(PathFile);
        File FilePath2 = new File(PathFile);
        if (FilePath.exists() && FilePath2.exists()) {
            try {
                String MD5File = getMD5(PathFile);
                String MD5File2 = getMD5(PathFile2);
                if (MD5File.contains(MD5File2)) return true;
                //new AlerDialogManager().showAlertDialog(MainActivity.Main, "Check Md5",getMD5(PathFile)+"\n_"+MD5String);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean MD5FileCheck(String PathFile, String MD5String) {
        File FilePath = new File(PathFile);
        if (FilePath.exists()) {
            try {
                String MD5File = getMD5(PathFile);
                Log.d("Check Md5", MD5File + "_..._" + MD5String);
                if (PathFile.contains("pino128.png")) {
                    Log.d("MD5 Pino", MD5String + "_..._" + MD5File);

                }
                if (MD5File.contains(MD5String)) return true;
                //new AlerDialogManager().showAlertDialog(MainActivity.Main, "Check Md5",getMD5(PathFile)+"\n_"+MD5String);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getMD5(String filePath) throws NoSuchAlgorithmException, IOException {

        String returnVal = "";
        try {
            InputStream input = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest md5Hash = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while (numRead != -1) {
                numRead = input.read(buffer);
                if (numRead > 0) {
                    md5Hash.update(buffer, 0, numRead);
                }
            }
            input.close();

            byte[] md5Bytes = md5Hash.digest();
            for (int i = 0; i < md5Bytes.length; i++) {
                returnVal += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return returnVal.toLowerCase();
    }


    public static void writeFile(String path, String mess) {
        try {
            File logFile = new File(path);
            if (!logFile.exists())
                logFile.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(logFile));
            output.write(mess);
            output.close();
            if (output != null) {
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readFile(String path){
        String result = "";
        try {
        BufferedReader inputStream = new BufferedReader(new FileReader(path));
        String l = null;
            while ((l = inputStream.readLine()) != null) {
                result = result + String.valueOf(l);
            }
        } catch (IOException e) {
            return "";
        }
        return result;
    }

    public static void demoData(){
        ArrayList<HistoryClass> historyList = new ArrayList<>();
        HistoryClass tmp;
        for(int i=0;i<100;i++){
            tmp = new HistoryClass();
            tmp.setContent("content "+i);
            if(i%3==1){
                tmp.setDate(1475168400);
            }else if (i%3==2){
                tmp.setDate(1476032400);
            }else{
                tmp.setDate(1476896400);
            }

            tmp.setID(i);
            historyList.add(tmp);
        }

        String json = new Gson().toJson(historyList);
        writeFile(Config.RootFolder+"history.isa",json);
    }

    public ArrayList<HistoryClass> getData(){
        historyList = new Gson().fromJson(readFile(Config.RootFolder+"history.isa"),new TypeToken<ArrayList<HistoryClass>>() {
        }.getType());
        qrData = this;
        return historyList;

    }

    public void addData(String content){
        if(historyList==null) QRData.historyList = new ArrayList<>();
        HistoryClass historyClass = new HistoryClass();
        historyClass.setID(historyList.size());
        historyClass.setDate(new Date().getTime());
        historyClass.setContent(content);
        historyList.add(historyClass);
        String json = new Gson().toJson(historyList);
        writeFile(Config.RootFolder+"history.isa",json);
    }

    public void deleteData(){
        writeFile(Config.RootFolder+"history.isa","");
    }

}

