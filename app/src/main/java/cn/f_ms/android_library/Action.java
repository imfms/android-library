package cn.f_ms.android_library;

/**
 * 名称-行为封装
 */
public abstract class Action implements Runnable {

    /**
     * 行为名称
     */
    private String name;

    public Action(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}