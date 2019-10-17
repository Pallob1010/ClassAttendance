package Interfaces;

import java.util.ArrayList;

import Model.ResultHolder;

public interface Backtrack {
    void Helper();

    void Invisible();

    void result(String series, String section, String course,int marks);

    void filebrowser(String series, String section, String course, ArrayList<ResultHolder>object,int totalclass);

}
