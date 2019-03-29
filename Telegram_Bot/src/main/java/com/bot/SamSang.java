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
	
	String regex = "^!(.*?)\\s|^!(.*?)$";// ��ɹ��� �Ľ��ϱ����� ���Խ�
	Pattern ptn = Pattern.compile(regex);
	Matcher matcher;

	@Override
	public void onUpdateReceived(Update update) {// ����ڷ� ���� �޼����� ������
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage send = new SendMessage().setChatId(update.getMessage().getChatId());
			String rec = update.getMessage().getText();// �޼����� �����´�.
			System.out.println("�����޼���: " + rec);// ���� �޼��� ���
			matcher = ptn.matcher(rec);// �޼������� ��ɾ� Ž��

			if (matcher.find())// ������ �ֳ� Ȯ��
			{
				String command = rec.substring(matcher.start(), matcher.end());
				String args = rec.substring(matcher.end(), rec.length());
				System.out.println("��ɹ�: " + command);

				switch (command) {
				case "!���ȯ�� ":
					try {
						send.setText("���� �����\n" + check.excute(args) + "�Դϴ�.").setParseMode(ParseMode.MARKDOWN);
					} catch (Exception e) {
					}
					break;
					
				case "!�̼�����":
					try {
						if(fba.excute().isEmpty())
						{
							send.setText("���� ��� �����Դϴ�.").setParseMode(ParseMode.MARKDOWN);
						}
						else
							send.setText("���� �����\n" + fba.excute() + "�Դϴ�.").setParseMode(ParseMode.MARKDOWN);
					} catch (Exception e) {
					}
					break;
				default:
					send.setText("�ȳ��ϼ���?\n������ ���͵帱���?");
					break;
				}

				// �޼����� ������ ����
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
