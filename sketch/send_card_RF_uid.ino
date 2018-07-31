
#include <SPI.h>
#include <MFRC522.h>
#include <Wire.h> 




#define SS_PIN 10
#define RST_PIN 9
#define LED_G 4 define green LED pin
#define LED_R 5 define red LED

MFRC522 mfrc522(SS_PIN, RST_PIN);    

int analogPin=0;
int analogValue=0;



void setup() 
{

  Serial.begin(9600);    
  SPI.begin();     
  mfrc522.PCD_Init(); 
 

  

}
void loop() {
 
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }

  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
 

  String content="" ;
  byte letter;
  for (byte i = 0; i< mfrc522.uid.size; i++) 
  {
     //Serial.print(mfrc522.uid.uidByte[i]  0x10   0   );
    // Serial.print(mfrc522.uid.uidByte[i], HEX);
   // content.concat(String(mfrc522.uid.uidByte[i],0x100));
     content+=(String(mfrc522.uid.uidByte[i], HEX));
   
  }
  content="#"+content;
    //content="#"+content+"#"+content+"#";
  content.toUpperCase();
  /*for (int i=0;i<3;i++){
  Serial.println(content);
  Serial.flush();
  delay(50);
  }
*/
Serial.println("###");
  //Serial.flush();  
  delay(50);
  Serial.println(content);
  Serial.flush();
delay(1500);
}
   
   
 
