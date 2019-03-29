package com.bot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.service.airCheck;
import com.service.findBadAir;

@Component("SamSang_bot")
public class SamSang extends TelegramLongPollingBot {
	@Autowired
	findBadAir fba;
	@Autowired
	airCheck check;

	String botname,token;
	
	String regex = "^!(.*?)\\s|^!(.*?)$";// 명령문을 파싱하기위한 정규식
	Pattern ptn = Pattern.compile(regex);
	Matcher matcher;

	@Override
	public void onUpdateReceived(Update update) {// 사용자로 부터 메세지를 받으면
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage send = new SendMessage().setChatId(update.getMessage().getChatId());
			String rec = update.getMessage().getText();// 메세지를 가져온다.
			System.out.println("받은메세지: " + rec);// 받은 메세지 출력
			matcher = ptn.matcher(rec);// 메세지에서 명령어 탐색

			if (matcher.find())// 조건이 있나 확인
			{
				String command = rec.substring(matcher.start(), matcher.end());
				String args = rec.substring(matcher.end(), rec.length());
				System.out.println("명령문: " + command);

				switch (command) {
				case "!대기환경 ":
					try {
						send.setText("다음 결과는\n" + check.excute(args) + "입니다.").setParseMode(ParseMode.MARKDOWN);
					} catch (Exception e) {
					}
					break;
					
				case "!미세먼지":
					try {
						if(fba.excute().isEmpty())
						{
							send.setText("전국 대기 맑음입니다.").setParseMode(ParseMode.MARKDOWN);
						}
						else
							send.setText("다음 결과는\n" + fba.excute() + "입니다.").setParseMode(ParseMode.MARKDOWN);
					} catch (Exception e) {
					}
					break;
				default:
					send.setText("안녕하세요?\n무엇을 도와드릴까요?");
					break;
				}

				// 메세지를 보내는 구문
				try {
					execute(send); // Call method to send the message
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

			}
		}

	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "SamSang_bot";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "585506534:AAE6pbi95xtWTilQv8Vs18ZCV2n_jif49BY";
	}


	public void setBotUsername(String name) {
		botname = name;
	}

	public void setBotToken(String token) {
		this.token = token;
	}
}
