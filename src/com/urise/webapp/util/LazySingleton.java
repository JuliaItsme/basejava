package com.urise.webapp.util;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder{
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
    public static LazySingleton getInstance(){
        return LazySingletonHolder.INSTANCE;
    }

//    public static LazySingleton getInstance() {
//       if(INSTANCE == null){
//            synchronized (LazySingleton.class){
//                if(INSTANCE == null){
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//   }
}
