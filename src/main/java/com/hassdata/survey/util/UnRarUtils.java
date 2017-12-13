package com.hassdata.survey.util;

import java.io.File;
import java.io.FileOutputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

public class UnRarUtils {
	 public static boolean unrar(File sourceRar, File destDir) throws Exception {
	        Archive archive = null;
	        FileOutputStream fos = null;
	        try {
	            archive = new Archive(sourceRar);
	            FileHeader fh = archive.nextFileHeader();
	            int count = 0;
	            File destFileName = null;
	            while (fh != null) {
	                String compressFileName = fh.getFileNameString().trim();
	                destFileName = new File(destDir.getAbsolutePath() + "/" + compressFileName);
	                if (fh.isDirectory()) {
	                    if (!destFileName.exists()) {
	                        destFileName.mkdirs();
	                    }
	                    fh = archive.nextFileHeader();
	                    continue;
	                }
	                if (!destFileName.getParentFile().exists()) {
	                    destFileName.getParentFile().mkdirs();
	                }
	                fos = new FileOutputStream(destFileName);
	                archive.extractFile(fh, fos);
	                fos.close();
	                fos = null;
	                fh = archive.nextFileHeader();
	            }

	            archive.close();
	            archive = null;
	            return true;
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if (fos != null) {
	                try {
	                    fos.close();
	                    fos = null;
	                } catch (Exception e) {
	                    //ignore
	                }
	            }
	            if (archive != null) {
	                try {
	                    archive.close();
	                    archive = null;
	                } catch (Exception e) {
	                    //ignore
	                }
	            }
	        }
	    }
}
