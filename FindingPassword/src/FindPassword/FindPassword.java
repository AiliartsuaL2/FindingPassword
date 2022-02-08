package FindPassword;

import java.io.*;
import java.util.*;
import java.nio.file.Files; 
import java.nio.file.Paths; 

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
 
public class FindPassword {
  private static final String text = "C://Ailiartsua//AccountServer.txt"; 
  
  public static void main(String args[]) {
		File file = new File(text); 
		Scanner stdIn = new Scanner(System.in);
		if (!file.exists()) { 
			try { 
				file.createNewFile(); 
		 } catch (IOException e) { 
			 System.err.println("파일 생성 실패"); 
			 return; 
		 		} 
		} 
		List<String> lines = null; 
		try { 
			lines = Files.readAllLines(Paths.get(text)); 
	  } catch (IOException e) { 
		  	System.err.println("파일 읽기 실패"); 
		  	return; 
	  	} 
		if (lines == null || lines.isEmpty()) { 
			System.out.println("파일이 비어있음"); 
			return; 
		} 
		int wordCount = lines.size(); 
		String[] Number = new String[wordCount]; 
		String[] Name = new String[wordCount]; 
		String[] ID = new String[wordCount]; 
		String[] Email = new String[wordCount];
		String[] Password = new String[wordCount];
		
		String[] splitedLine = null; 
		String line = null; 
		
		for (int i = 0; i < wordCount; i++) { 
			line = lines.get(i); 
			splitedLine = line.split(" "); 
			
			Number[i] = splitedLine[0]; 
			Name [i] = splitedLine[1];
			ID[i] = splitedLine[2]; 
			Email[i] = splitedLine[3]; 
			Password[i] = splitedLine[4]; 
		} 
		
		System.out.print("아이디를 입력하세요.");	// 키값을 입력
		String ky = stdIn.next();
		
		int idx = Arrays.binarySearch(ID, ky);		// 배열 x에서 값이 ky인 요소를 검색
		if (idx < 0)
			System.out.println("해당 키워드가 없습니다.");
		else
		System.out.println(Email[idx]+"로 메일을 전송하였습니다.");
  
			// 메일 전송
    final String bodyEncoding = "UTF-8"; //콘텐츠 인코딩
    
	Random rand = new Random(); 
	int a = rand.nextInt(9); 
	int b = rand.nextInt(9); 
	int c = rand.nextInt(9); 
	int d = rand.nextInt(9); 
	int e = rand.nextInt(9); 
	int f = rand.nextInt(9);
	String SecNum = a+" "+b+" "+c+" "+d+" "+e+" "+f; // 인증번호를 랜덤넘버 6자리로 구성
	String InSecNum;
    String fromEmail = "leejuho1712041@gmail.com";
    String subject = "메일 발송 테스트";
    String fromUsername = "암호관리자";
    String toEmail = Email[idx]; // 발신자 관리
    
    final String username = "leejuho1712041@gmail.com";         
    final String password = "grhiayyfrvsskqxb"; // 구글계정
    Scanner sc = new Scanner(System.in);
    
    // 메일에 출력할 텍스트
    StringBuffer sb = new StringBuffer();
    sb.append("<h3>안녕하세요 암호관리자입니다.<h3> \n");
    sb.append("<h3>요청하신 인증번호는 : "+SecNum+" 입니다.<h3> \n");    
    String html = sb.toString();
    
    // 메일 옵션 설정
    Properties props = new Properties();    
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.auth", "true");
 
    props.put("mail.smtp.quitwait", "false");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");
    
    try {
      // 메일 서버  인증 계정 설정
      Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      };
      
      // 메일 세션 생성
      Session session = Session.getInstance(props, auth);
      
      // 메일 송/수신 옵션 설정
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(fromEmail, fromUsername));
      message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail, false));
      message.setSubject(subject);
      message.setSentDate(new Date());
      
      // 메일 콘텐츠 설정
      Multipart mParts = new MimeMultipart();
      MimeBodyPart mTextPart = new MimeBodyPart();
      MimeBodyPart mFilePart = null;
 
      // 메일 콘텐츠 - 내용
      mTextPart.setText(html, bodyEncoding, "html");
      mParts.addBodyPart(mTextPart);
            
      // 메일 콘텐츠 설정
      message.setContent(mParts);
      
      // MIME 타입 설정
      MailcapCommandMap MailcapCmdMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
      MailcapCmdMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
      MailcapCmdMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
      MailcapCmdMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
      MailcapCmdMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
      MailcapCmdMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
      CommandMap.setDefaultCommandMap(MailcapCmdMap);
 
      // 메일 발송
      Transport.send(message );      
      System.out.println("이메일을 통해 받은 인증번호를 입력해주세요(띄어쓰기 포함)");
      InSecNum = sc.nextLine();
      
      if(InSecNum.equals(SecNum)) {
	      System.out.println("인증에 성공하였습니다!");  
          System.out.println("해당 아이디 : "+ID[idx]+" 의 비밀번호는 : "+Password[idx]+" 입니다.");
	      }
      else {
	      System.out.println("인증번호를 잘못 입력하였습니다. 프로그램을 다시 시작해 주십시오.");  
	      } 
      
    } catch ( Exception E ) {
      E.printStackTrace();
    }
  }
}