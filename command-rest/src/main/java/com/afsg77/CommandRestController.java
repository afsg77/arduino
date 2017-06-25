//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//               Code and manual by afsg77
// Application for sending commands to arduino through a REST service.

package com.afsg77;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.MediaType;

/**
 * Main controller clase for the REST service.
 * This class is a simple REST service with a command string value.
 * If the class contains an active command the home execute value is true.
 */
@SpringBootApplication
@RestController
public class CommandRestController
{
	/**
	 * This method maps the root of the application and return a json with 
	 * the should execute value. True if the class contains a command.
	 */
	@RequestMapping(value = {"/"},
		produces = MediaType.APPLICATION_JSON_VALUE)
	public String home() 
	{
		String isActive;
		if (this.command == null || this.command.isEmpty())
		{
			isActive = this.getExecuteJson("false");
		}
		else
		{
			isActive = this.getExecuteJson("true");
		}

		return isActive;
	}

	/**
	 * This method gets the command.
	 */
	@RequestMapping(value = {"/GetCommand"},
		produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCommand() 
	{
		return this.getActionJson(this.command);
	}

	/**
	 * This method set the command.
	 */
	@RequestMapping(value = {"/SetCommand"},
		produces = MediaType.APPLICATION_JSON_VALUE)
	public String setCommand(@RequestParam("command") String command) 
	{
		this.command = command;
		return this.getStatusJson("true");
	}

	/**
	 * This method cleans the command.
	 */
	@RequestMapping(value = {"/CleanCommand"},
		produces = MediaType.APPLICATION_JSON_VALUE)
	public String cleanCommand()
	{
		this.command = null;
		return this.getStatusJson("true");
	}

	/**
	 * This method builds the action json.
	 */
	public String getActionJson(String action)
	{
		return "{\"action\":\"" + action + "\"}";
	}
	
	/**
	 * This method builds the status json.
	 */
	public String getStatusJson(String status)
	{
		return "{\"status\":" + status + "}";
	}
	
	/**
	 * This method builds the execute json.
	 */
	public String getExecuteJson(String execute)
	{
		return "{\"execute\":" + execute + "}";
	}

	/**
	* <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking">
	* App Engine health checking</a> requires responding with 200 to {@code /_ah/health}.
	*/
	@RequestMapping("/_ah/health")
	public String healthy()
	{
		// Message body required though ignored
		return "Still surviving.";
	}

	public static void main(String[] args)
	{
		SpringApplication.run(CommandRestController.class, args);
	}

	/**
	 * The main command.
	 */
	protected String command;
}
