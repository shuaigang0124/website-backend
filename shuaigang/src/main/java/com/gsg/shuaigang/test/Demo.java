package com.gsg.shuaigang.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * TODO 有五个学生，每个学生有3门课的成绩，从键盘输入以上数据（包括学生号，姓名，三门课成绩），
 * TODO 计算出平均成绩，把原有的数据和计算出的平均分数存放在磁盘文件 "stud "中。
 * @author shuaigang 
 * @date 2022/6/28 12:40
 */
public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Student[] stus = new Student[5];
        System.out.println("请输入五个学生的学号，姓名，三门成绩:（用,隔开）");
        for(int i=0;i<stus.length;i++){
            String str = sc.nextLine();
            String[] strs = str.split(",");
            Student stu = new Student();
            stu.sid = strs[0];
            stu.name = strs[1];
            stu.oneGrade =Integer.parseInt(strs[2]);
            stu.twoGrade = Integer.parseInt(strs[3]);
            stu.threeGrade = Integer.parseInt(strs[4]);
            stu.averageGrade = (stu.oneGrade+stu.twoGrade+stu.threeGrade)/3;
            stus[i] = stu;
        }
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("stud.txt"));
            for (Student student : stus) {
                System.out.println(student.name + "的平均成绩为:" + student.averageGrade);
                out.write("学号:" + student.sid
                        + ",姓名:" + student.name
                        + ",第一门课成绩:" + student.oneGrade
                        + ",第二门课成绩:" + student.twoGrade
                        + ",第三门课成绩:" + student.threeGrade
                        + ",平均成绩:" + student.averageGrade);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Student {
    String sid;
    String name;
    int oneGrade;
    int twoGrade;
    int threeGrade;
    int averageGrade;
}
/**
 * TODO 冒泡排序 1,30,50,999,66,23,18,16
 * @author shuaigang
 * @date  2022/6/28 12:50
 */
class Demo02 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,30,50,999,66,23,18,16};
        for(int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    //交换相邻两个数
                    int temp=arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}

/**
 * TODO 海滩上有一堆桃子，五只猴子来分。
 * TODO 第一只猴子把这堆桃子凭据分为五份，多了一个，这只猴子把多的一个扔入海中，拿走了一份。
 * TODO 第二只猴子把剩下的桃子又平均分成五份，又多了一个，它同样把多的一个扔入海中，拿走了一份，
 * TODO 第三、第四、第五只猴子都是这样做的，问海滩上原来最少有多少个桃子
 * @author shuaigang
 * @date  2022/6/28 13:00
 */
class Demo03 {
    public static void main(String[] args) {
        //猴子的数目
        int num = 5;
        //假设最后一只猴子的桃子数目为1
        int n = 1;
        //开始的桃子数目
        int startNum = 0;
        for(int i = 0; i < num; ++i ) {
            if(i == 0) {
                startNum = 5*n + 1;
            }else {
                if(startNum%4 != 0) {
                    n++;
                    i = -1;
                }else {
                    startNum = 5 * (startNum/4) + 1;
                }
            }
        }
        System.out.println(startNum);
    }
}