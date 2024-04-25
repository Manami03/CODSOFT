/* task-2
STUDENT GRADE CALCULATOR */
import java.util.*;
import java.util.Scanner;
public class StudentGradeCalculator{
	public static void main(String args[]){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number of subject : ");
		int numberSubject=sc.nextInt();
		if (numberSubject<=0){
			System.out.println("NUMBER INVALID");
			return;
		}
		int totalMarks=0;
		int maxMarksSubjectWise=100;
		for(int i=1;i<=numberSubject;i++)
		{
			System.out.println("Enter the marks in subject " + i);
			int marks=sc.nextInt();
			if(marks<0||marks>maxMarksSubjectWise)
			{
				System.out.println("OUT OF RANGE");
				i--;
			}
			else{
				totalMarks+=marks;
			}
		}
		double averagePercentage=(double) totalMarks/numberSubject;
		String grade;
		if(averagePercentage>=90)
		{
			grade="O";
		}
		else if(averagePercentage>=80)
		{
			grade="A";
		}
		else if(averagePercentage>=70)
		{
			grade="B";
		}
		else if(averagePercentage>=60)
		{
			grade="C";
		}
		else if(averagePercentage>=50)
		{
			grade="D";
		}
		else{
			grade="F";
		}
		System.out.println("totalMarks: " +totalMarks);
		System.out.println("averagePercentage: " +averagePercentage + "%");
		System.out.println("Grade" +grade);
	}
}
