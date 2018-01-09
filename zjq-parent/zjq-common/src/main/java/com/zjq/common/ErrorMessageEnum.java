package com.zjq.common;

public enum ErrorMessageEnum {
	//-------------------- System Question Start ---------------------------
	  //System.Question.Insert Or System.Question.Update,10901##
	  Question_Insert_Or_Update_Empty_Question_Object("1090101","You can't insert or update an empty question."),
	  Question_Insert_Or_Update_Empty_Code("1090102","Question code can't be empty."),
	  Question_Insert_Or_Update_Empty_Type("1090103","Question type can't be empty."),
	  Question_Insert_Or_Update_Type_Not_Valid("1090104","Question type is not valid"),
	  Question_Insert_Or_Update_Empty_Subject("1090105","Question subject can't be empty."),
	  Question_Insert_Or_Update_Code_Already_Exist("1090106","Code [%s] already exist, please try another."),
	  Question_Insert_Or_Update_Empty_Reference_List("1090107","Question reference can't be empty."),
	  Question_Insert_Or_Update_Status_Not_Valid("1090108","Question status [%s] is not valid"),
	  Question_Reference_Insert_Empty_Reference("1090109","You can't insert empty question reference."),
	  Question_Reference_Insert_Empty_Question_ID("1090110","Question ID can't be empty."),
	  Question_Reference_Insert_Empty_Reference_ID("1090111","Question reference ID can't be empty."),
	  Question_Insert_Or_Update_Empty_Category_List("1090112","Question category can't be empty."),
	  Question_Category_Insert_Empty_Category("1090113","You can't insert empty question category."),
	  Question_Category_Insert_Empty_Question_ID("1090114","Question ID can't be empty."),
	  Question_Category_Insert_Empty_Category_ID("1090115","Question category ID can't be empty."),
	  Question_Insert_Or_Update_Empty_Numberic_Answer_Correct_Number("1090116","Question range start or end can't be empty."),
	  Question_Insert_Or_Update_Empty_Logical_Answer_Correct_Response("1090117","The correct response can't be empty."),
	  Question_Insert_Or_Update_Empty_Optional_Answer_Question_Optional_Answers("1090118","The question optional answers can't be empty."),
	  Question_Optional_Answer_Insert_Empty_Question_Optional_Answer("1090119","You can't insert empty question optional answer."),
	  Question_Optional_Answer_Insert_Empty_Question_ID("1090120","Question ID can't be empty."),
	  Question_Optional_Answer_Insert_Empty_Answer_Sequence("1090121","Answer sequence can't be empty."),
	  Question_Optional_Answer_Insert_Empty_Answer_Text("1090122","Answer text can't be empty."),
	  //System.Question.Insert,10902##
	  Question_Insert_ID_Not_Empty("1090201","question id must be null"),
	  //System.Question.Update,10903##
	  Question_Update_Question_Not_Exist("1090301","The question [%d] doesn't exist."),
	  Question_Update_Empty_Code("1090302","Question code can't be empty."),
	  Question_Update_Empty_Type("1090303","Question type can't be empty."),
	  Question_Update_Empty_Subject("1090304","Question subject can't be empty."),
	  Question_Update_Empty_Category_List("1090305","Question category can't be empty."),
	  Question_Update_Code_Already_Exist("1090306","Code [%s] already exist, please try another."),
	  Question_Update_Wrong_Type("1090307","Question type don't exist."),
	  //System.Question.Delete,10904##
	  Question_Delete_Question_Not_Exist("1090401","The question [%d] doesn't exist."),
	  //-------------------- System Question End ---------------------------
  ;
  
  private ErrorMessageEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
  
  private String code;
  private String desc;
  
  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
  
}
