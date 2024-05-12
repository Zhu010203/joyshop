package com.htu.common;


//封装工具类,保存当前使用用户id
public class BaseContext {
    //当前线程对象
    private static InheritableThreadLocal<Long> threadLocal=new InheritableThreadLocal<>();
    //存储到当前线程
    public static synchronized void setCurrentId(Long id){
        threadLocal.set(id);
    }
    //从当前线程取出
    public static synchronized Long getCurrentId(){
        if (threadLocal.get()==null){
            return 123456789L;
        }
        return threadLocal.get();
    }
}
