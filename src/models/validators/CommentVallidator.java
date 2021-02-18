package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Comment;

public class CommentVallidator {
    public static List<String> validate(Comment c) {
        List<String> errors = new ArrayList<String>();


        String comment_error = _validateContent(c.getComment());
        if(!comment_error.equals("")) {
            errors.add(comment_error);
        }

        return errors;
    }


    private static String _validateContent(String comment) {
        if(comment == null || comment.equals("")) {
            return "内容を入力してください。";
            }

        return "";
    }
}
