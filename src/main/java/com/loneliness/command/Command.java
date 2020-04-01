package com.loneliness.command;

public interface Command {
    <T,R> R execute(T data);
}
