package com.zjq.common.service;

/**
 * All of service message will be defined in here. The following is the dispatch rules:
 * 
 * <ul>
 * <li>System - 1, MSG_1#####
 * <ul>
 * <li>User - 1, MSG_11####</li>
 * <li>Role - 2, MSG_12####</li>
 * <li>Module - 3, MSG_13####</li>
 * <li>Permission - 4, MSG_14####</li>
 * <li>Org - 5, MSG_15####</li>
 * <li>QCode - 6, MSG_16####</li>
 * <li>SmartForm - 7, MSG_17####</li></li>
 * </ul>
 *
 * <li>EHS - 2, MSG_2#####</li>
 * <ul>
 * <li>Basic - 1, MSG_21####</li>
 * <ul>
 * <li>Physical Structure - 1, MSG_211###</li>
 * <ul>
 * <li>Facility - 1, MSG_2111##</li>
 * <li>Building - 2, MSG_2112##</li>
 * <li>Location - 3, MSG_2113##</li>
 * <li>Contact - 4, MSG_2114##</li>
 * <li>Activity - 5, MSG_2115##</li>
 * <li>Coopreate Structure - 6, MSG_2116##</li>
 * <li>Employee - 7, MSG_2117##</li>
 * <li>Job - 8, MSG_2118##</li>
 * </ul>
 * </ul>
 * 
 * 
 * </ul> </ul>
 * 
 * 
 * 
 * @author ronnieking
 *
 */
public interface IErrorMessage {

  // MSG_1### is belong system module
  String MSG_100001 = "The value of version can't be empty or just 0!!!";

  // MSG_2### is belong EHS Basic Module
  String MSG_200000 = "";

  String MSG_211401 = "Error: System can't add an empty contact!!!";
  String MSG_211402 = "Error: System can't update an empty contact!!!";
  String MSG_211403 = "Error: Some contact ID is not exist in current database!!!";

  // MSG
  String MSG_210000 = "";

  String MSG_220000 = "";

  String MSG_230000 = "";

  String MSG_240000 = "";

  String MSG_250000 = "";

}
