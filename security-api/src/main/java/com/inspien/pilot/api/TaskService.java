package com.inspien.pilot.api;

public interface TaskService {
    String register(String id, Task executor) throws Exception;

    void unregister(String id) throws Exception;

    void execute(String id) throws Exception;
}
