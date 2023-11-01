package com.example.swagger.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class JsonRefResolver_bak {
    public static String modifyInput(String input) {
        // 循环查找 "$ref": {

        int index = input.indexOf("\"$ref\":{");
        while (index != -1) {
            // 删除 "$ref": {
            input = input.substring(0, index) + input.substring(index + 8);

            int flag = 1;
            int i = index;
            while (flag != 0 && i < input.length()) {
                char currentChar = input.charAt(i);
                i++;

                if (currentChar == '{') {
                    flag++;
                } else if (currentChar == '}') {
                    flag--;
                }
            }

            // 删除匹配的 "}"
            input = input.substring(0, i - 1) + input.substring(i);

            // 继续寻找下一个 "$ref": {
            index = input.indexOf("\"$ref\": {");
        }

        return input;
    }
}
