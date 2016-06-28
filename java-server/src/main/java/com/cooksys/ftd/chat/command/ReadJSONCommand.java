package com.cooksys.ftd.chat.command;

public class ReadJSONCommand extends AbstractCommand {

	public ReadJSONCommand() {
		super ("/readjson",
			   "(file name)",
			   "read json object on server");
	}
	
	
}
