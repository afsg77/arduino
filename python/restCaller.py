#                       _oo0oo_
#                      o8888888o
#                      88" . "88
#                      (| -_- |)
#                      0\  =  /0
#                    ___/`---'\___
#                  .' \\|     |# '.
#                 / \\|||  :  |||# \
#                / _||||| -:- |||||- \
#               |   | \\\  -  #/ |   |
#               | \_|  ''\---/''  |_/ |
#               \  .-\__  '-'  ___/-. /
#             ___'. .'  /--.--\  `. .'___
#          ."" '<  `.___\_<|>_/___.' >' "".
#         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
#         \  \ `_.   \_ __\ /__ _/   .-` /  /
#     =====`-.____`.___ \_____/___.-`___.-'=====
#                       `=---='
#               Code and manual by afsg77
# Application for sending commands to arduino through a REST service.

# Needed libraries.
import urllib.request, json, time, serial

# Global variables.
restURL = "https://absolute-gantry-followers.appspot.com/" # Insert the url of your server where you deployed with your spring boot. Local host for testing.
arduinoSerial = serial.Serial('/dev/ttyACM0', 9600) # Arduino serial port. This works for linux.

##
# This class Handles the JSON calls.
##
class GetJson:
	# This method get the execute value from the REST service.
	def getShouldExecute(self):
		return self.getJson(restURL)
	# This method gets the command from the REST service.
	def getCommand(self):
		return self.getJson(restURL + "GetCommand")
	# This method clean the command in the REST service.
	def cleanCommand(self):
		return self.getJson(restURL + "CleanCommand")
	# This method handles the JSON call.
	def getJson(self, requestURL):
		try:
			with urllib.request.urlopen(requestURL) as url:
				data = json.loads(url.read().decode())
			return data
		except:
			pass

#### Execution
rest = GetJson() # Handler for JSON calls.
i = 0 # Requests counter.

# Start running the command service.
while (True):
	i=i+1
	shouldExecute = False # Default to false.
	
	try:
		# Try to get the JSON properties.
		shouldExecute = rest.getShouldExecute()['execute']
		if (shouldExecute):
			command = rest.getCommand()['action']
	except:
		# If the JSON parsing goes wrong default the execution to false.
		shouldExecute = False

	if (shouldExecute):
		print(command)
		arduinoSerial.write(bytes(command, 'UTF-8')) # Send command to arduino.
		rest.cleanCommand() # Tell the REST service the command was executed and clean it.
	else:
		print("No Action, Request#: " + str(i))
	time.sleep(3) # Wait three seconds before pinging the service again.

arduinoSerial.close()