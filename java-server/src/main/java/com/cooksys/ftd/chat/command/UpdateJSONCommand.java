package com.cooksys.ftd.chat.command;

public class UpdateJSONCommand extends AbstractCommand {
	
	public UpdateJSONCommand() {
		super ("/updatejson",
			   "(file name)",
			   "Update JSON file on server");
	}
}
