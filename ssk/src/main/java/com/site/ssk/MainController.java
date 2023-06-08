package com.site.ssk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.site.ssk.account.Account;
import com.site.ssk.account.AccountRepository;
import com.site.ssk.meeting.Meeting;
import com.site.ssk.meeting.MeetingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController	// RestApi용 컨트롤러, 데이터(JSON)반환
public class MainController {
	
	@PostMapping("/translate")
	public String create(@RequestBody Map<String,String> requestData) {
		
		// 번역 요청이 들어오면, json에 있던 정보를 저장
		translate t = new translate();
		
		requestData.forEach((key, value) -> {
			if(key == "TerminologyNames") {
				t.TerminologyNames = value;
			}
			if(key == "SourceLanguageCode") {
				t.SourceLanguageCode = value;
			}
			if(key == "TargetLanguageCode") {
				t.TargetLanguageCode = value;
			}
			if(key == "Text") {
				t.Text = value;
			}
	    });
		
		AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        
        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds.getCredentials()))
                .withRegion("ap-northeast-2")
                .build();
        // String text = "Anti-circumvention refers to laws which prohibit the circumvention of technological barriers for using a digital good in certain ways which the rightsholders do not wish to allow.";
        // 서버야 번역해줘
        TranslateTextRequest request3 = new TranslateTextRequest()
                .withText(t.Text)
                .withTerminologyNames(t.TerminologyNames)
                .withSourceLanguageCode(t.SourceLanguageCode)
                .withTargetLanguageCode(t.TargetLanguageCode);
        TranslateTextResult result3  = translate.translateText(request3);
        // result3이 순수하게 번역된 문장
        System.out.println("그냥번역: " + result3.getTranslatedText());
        
       
        int termsize = result3.getAppliedTerminologies().get(0).getTerms().size();
        System.out.println("termsize : " + termsize);
        
        for(int i = 0; i < termsize ; i++) {
        	String source = result3.getAppliedTerminologies().get(0).getTerms().get(i).getSourceText();
        	String target = result3.getAppliedTerminologies().get(0).getTerms().get(i).getTargetText();
        	System.out.println(source + target);
        }
        
        // modified는 순수번역 문장에서 
        String modified = t.Text;
        if(t.SourceLanguageCode.equals("en")) {
        	System.out.println("영어를 한국어로");
        	
        	if (result3.getAppliedTerminologies().get(0).getTerms().size()!=0) { 
            	System.out.println("fadfasfasdfasfsdafasdfasdfasdf");
            	String term = "";
            	String targetTerm = "";
            	for(int i = 0; i < termsize ; i++) {
                	term = result3.getAppliedTerminologies().get(0).getTerms().get(i).getSourceText();
                	targetTerm = result3.getAppliedTerminologies().get(0).getTerms().get(i).getTargetText();
                	System.out.println(targetTerm);
                    modified = t.Text.replace(term, '[' + term + ']');
                    System.out.println("modified:"+modified);
                }

                TranslateTextRequest modifiedRequest = new TranslateTextRequest()
                        .withText(modified)
                        .withTerminologyNames(t.TerminologyNames)
                        .withSourceLanguageCode(t.SourceLanguageCode)
                        .withTargetLanguageCode(t.TargetLanguageCode);
                
                TranslateTextResult modifiedResult = translate.translateText(modifiedRequest);
                String modifiedText = modifiedResult.getTranslatedText();
                int targetIndex = modifiedText.indexOf(targetTerm); 
                String result = modifiedText.substring(0,targetIndex-1) + targetTerm + modifiedText.substring(targetIndex + targetTerm.length()+2);
                System.out.println("result : "+ result);
                return result;
            } else return result3.getTranslatedText();
        } else return result3.getTranslatedText();
            
	}
}