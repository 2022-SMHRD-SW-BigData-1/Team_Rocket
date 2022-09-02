package view;

import java.util.Scanner;

import controller.Controller_DAO;
import model.User_Pokemon;
import model.User_VO;

public class View_User {

	public static void main(String[] args) {
//=========================================================================================================	
//	로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 
//=========================================================================================================	
		Scanner sc = new Scanner(System.in);

		Controller_DAO dao = new Controller_DAO();
		// 유저 가입, 로그인에 따른 포켓몬 생성
		User_Pokemon userPokemon = null;
		// 유저 가입, 로그인에 따른 정보 초기화 차이
		User_VO userVO = null;

		while (true) {
			System.out.print("[1]회원가입  [2]로그인>>");
			int menu = sc.nextInt();

			if (menu == 1) {
				System.out.println("==========등록==========");
				System.out.println("ID : ");
				String id = sc.next();
				System.out.println("PW : ");
				String pw = sc.next();
				System.out.println("nick : ");
				String nick = sc.next();

				int cnt = dao.join(id, pw, nick);
				if (cnt > 0) {
					System.out.println("등록 성공");
				} else {
					System.out.println("등록 실패");
				}
				// 유저 포켓몬 선택  흐름 int check_num == 선택 번호
				
				
				userPokemon = new User_Pokemon();
				userVO = new User_VO(id, pw, nick);
				break;
			} else if (menu == 2) {
				// 로그인 기능 연결
				System.out.println("ID : ");
				String id = sc.next();
				System.out.println("PW : ");
				String pw = sc.next();
				boolean res = dao.login(id, pw);
				if (res == true) {
					System.out.println("로그인 성공");
				} else {
					System.out.println("로그인 실패");
				}
				userPokemon = new User_Pokemon(id, pw);
				userVO = new User_VO(id, pw);
				break;
			}
		}
		// 뷰 유저 수정.
		// 2차 수정
//=========================================================================================================	
//	로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 로그인 
//=========================================================================================================	
		System.out.println("-------------------------------");
		System.out.println("[1]사냥터 [2]체육관도전 [3]상태창 확인 [4]치료센처 [5]세이브 [6]종료");
		//임시 상태창  check_pokemon(userPokemon){
		
	}
}
