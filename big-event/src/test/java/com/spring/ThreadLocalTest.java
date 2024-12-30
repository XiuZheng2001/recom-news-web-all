package com.spring;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocal() {
        ThreadLocal tl = new ThreadLocal();

        new Thread(()->{
            tl.set("xy");
            System.out.println(Thread.currentThread().getName() + tl.get());

        }, "蓝色").start();
        new Thread(()->{
            tl.set("yc");
            System.out.println(Thread.currentThread().getName() + tl.get());

        }, "绿色").start();

    }
}
