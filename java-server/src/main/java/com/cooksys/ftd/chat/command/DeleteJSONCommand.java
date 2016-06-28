package com.cooksys.ftd.chat.command;

public class DeleteJSONCommand extends AbstractCommand {
	public DeleteJSONCommand() {
		super ("/deletejson",
			   "(file name)",
			   "Delete JSON file on server");
	}
}
