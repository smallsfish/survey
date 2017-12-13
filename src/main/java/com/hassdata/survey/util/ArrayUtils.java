package com.hassdata.survey.util;

public class ArrayUtils {
    public static boolean idExists(String[][] strs,String str,boolean isButton){
        int count=0;
        for (String[] ss : strs){
            if(isButton){
                if(str.equals(ss[2])){
                    count++;
                }
            }else{
                if(str.equals(ss[1])){
                    count++;
                }
            }
        }
        return count>0;
    }
    public static Integer getIndex(String[][] strs,String str){
        Integer index=null;
        for (String[] ss : strs){
            if(ss[1].equals(str)){
                return Integer.parseInt(ss[0]);
            }
        }
        return index;
    }
}
