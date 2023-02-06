package com.theinfinity.srpingbootinstagram.entity;

public final class Views {
    public interface Id{}
    public interface IdName extends Id{}{}

    public interface FullComment extends IdName{}{}
    public interface FullPost extends IdName{}
    public interface FullProfile extends IdName{}
}
