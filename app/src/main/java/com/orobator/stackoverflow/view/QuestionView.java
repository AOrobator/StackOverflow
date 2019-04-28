package com.orobator.stackoverflow.view;

import java.util.List;

public class QuestionView {
    public final long id;
    public final String title;
    public final String score;
    public final List<String> tags;

    public QuestionView(long id, String title, String score, List<String> tags) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.tags = tags;
    }
}
