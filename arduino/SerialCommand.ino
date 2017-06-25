#define LED_PIN 13
#define RELAY_ON 0
#define RELAY_OFF 1
#define Relay_1  2

void setup() 
{
  digitalWrite(Relay_1, RELAY_OFF);
  pinMode(Relay_1, OUTPUT);
  pinMode(LED_PIN, OUTPUT); 
  Serial.begin(9600);
}

void loop() 
{
  if (Serial.available() > 0) 
  {
      // Read the command from serial.
      String command = Serial.readString();

      // Choose the right action for the command.
      if (command == "ledOn") 
      {
         digitalWrite(LED_PIN, HIGH);
      }
      else if (command == "ledOff")
      {
         digitalWrite(LED_PIN, LOW);
      }
      else if (command == "74815")
      {
        // Turn on for three seconds.
         digitalWrite(Relay_1, RELAY_ON);
         delay(3000);
         digitalWrite(Relay_1, RELAY_OFF);
      }
   }
}
