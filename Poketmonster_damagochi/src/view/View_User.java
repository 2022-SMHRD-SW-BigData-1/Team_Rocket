package view;

import java.io.IOException;
import java.util.Scanner;
import controller.Battle;
import controller.Bgmplayer;
import controller.Controller_DAO;
import controller.image;
import model.Pokemons;
import model.User_Pokemon;
import model.User_VO;
import model.story;

public class View_User {
	public static void main(String[] args) {
		User_Pokemon up = new User_Pokemon();
		Pokemons[] first_pokemon = up.getPokemonsArray();
		Battle battle = new Battle(first_pokemon);
		Scanner sc = new Scanner(System.in);
		Controller_DAO dao = new Controller_DAO();
		image im = new image();
		// 유저 가입, 로그인에 따른 포켓몬 생성
		User_Pokemon userPokemon = null;
		// 유저 가입, 로그인에 따른 정보 초기화 차이
		story s = new story();
		User_VO userVO = null;
		Bgmplayer bgm = new Bgmplayer();
		// 메인 타이틀
		s.title();
		// 회원가입 , 로그인
		System.out.print("[1]회원가입  [2]로그인 >> ");
		int menu = sc.nextInt();
		System.out.println("\n");
		if (menu == 1) {
			while (true) {
				int choose;
				//s.opening();
				System.out.println("==========등록==========");
				System.out.println("\n");
				System.out.print("ID : ");
				String id = sc.next();
				System.out.print("PW : ");
				String pw = sc.next();
				System.out.print("nick : ");
				String nick = sc.next();
				int cnt = dao.join(id, pw, nick);
				if (cnt > 0) {
					System.out.println("\n");
					System.out.println("등록 성공");
					System.out.println("\n");
					userVO = new User_VO(id, pw, nick);
					choose = s.choose();
					battle.arrinsert(first_pokemon, choose);
					dao.joininsert(first_pokemon, choose, id);
					break;
				} else {
					System.out.println("\n");
					System.out.println("등록 실패");
					System.out.println("\n");
					continue;
				}
			}
			// 로그인 기능 연결
		} else if (menu == 2) {
			while (true) {
				System.out.print("ID : ");
				String id = sc.next();
				System.out.print("PW : ");
				String pw = sc.next();
				boolean res = dao.login(id, pw);
				if (res == true) {
					// call_Pokemon 메소드 사용시 포켓몬 담긴 배열 마지막에 유저의 포켓몬 저장
					userVO = new User_VO(id, pw);
					dao.call_Pokemon(first_pokemon, userVO.getId());
					System.out.println("\n");
					System.out.println("로그인 성공");
				} else {
					System.out.println("\n");
					System.out.println("로그인 실패");
					System.out.println("\n");
					continue;
				}
				break;
			}
		}
		int end = 0;
		while (true) {
			bgm.stop();
			battle.bgmstop();
			if (end == 1) {
				break;
			}
			System.out.print("===================== < 선택해주세요 > =====================");
			System.out.println("\n");
			System.out.println("[1]사냥터 [2]체육관도전 [3]상태창 확인 [4]치료센터 [5]진화 [6]세이브 [7]종료");
			System.out.println("\n");
			System.out.print("입력해주세요 : ");
			int move = sc.nextInt();
			if (move == 1) {
				System.out.println("\n");
				System.out.print("사냥터로 이동합니다.");
				System.out.println("\n");
				s.field();
				bgm.play("포켓몬발견");
				battle.vs(first_pokemon);
				bgm.stop();
			} else if (move == 2) {
				while (true) {
					System.out.println("\n");
					System.out.print("체육관에 도전하시겠습니까? (Y/N)");
					System.out.print("\n");
					System.out.print("입력주세요 : ");
					String yn = sc.next();
					if (yn.equals("y")) {
						if (first_pokemon[first_pokemon.length - 1].getLevel() >= 5) {
							s.gym1();
							battle.vs(first_pokemon);
							s.gym1end();
							if (first_pokemon[first_pokemon.length - 1].getHp() > 0) {
								System.out.println("\n");
								battle.endheal(first_pokemon);
								s.roketdan();
								battle.vs(first_pokemon);
								if (first_pokemon[first_pokemon.length - 1].getHp() > 0) {
									s.ending();
									end++;
								}
								break;
							}
						} else if (first_pokemon[first_pokemon.length - 1].getLevel() < 5) {
							System.out.println("레벨 조건이 충족되지 않았습니다.");
							break;
						}

					} else if (yn.equals("n")) {
						System.out.println("\n");
						break;
					} else {
						System.out.println("\n");
						System.out.print("잘못 입력하셨습니다.");
						continue;
					}
				}
			} else if (move == 3) {
				System.out.println("\n");
				System.out.println("   상태창을 출력합니다.");
				System.out.println("\n");
				battle.status(first_pokemon);
			} else if (move == 4) {
				System.out.println("\n");
				System.out.print("치료센터로 이동합니다.");
				System.out.println("\n");
				battle.heal(first_pokemon);
			} else if (move == 5) { // 진화
				int level = first_pokemon[first_pokemon.length - 1].getLevel();
				if (level >= 5) {
					bgm.play("진화");
					poke_up(first_pokemon, im);
					bgm.stop();
					

				} else {
					System.out.println("레벨 조건이 충족되지 않았습니다.");
					System.out.println("\n");
				}
			} else if (move == 6) {
				System.out.println("\n");
				System.out.print("현재까지 플레이 내역을 저장합니다.");
				System.out.println("\n");
				dao.save(first_pokemon, userVO.getId());
			} else if (move == 7) {
				dao.save(first_pokemon, userVO.getId()); // 해당 유저의 아이디를 받아 아이디로 찾아 유저 포켓몬 정보 최신화
				System.out.println("\n");
				System.out.print("게임을 종료합니다.");
				break;
			} else {
				System.out.println("\n");
				System.out.print("잘못 입력하셨습니다!!");
			}
		}
	}


	private static void poke_up(Pokemons[] first_pokemon, image im) {
		String pk_name = first_pokemon[first_pokemon.length - 1].getPokemon_Nmae();
		String name2=null;
		for (int i = 0; i < first_pokemon.length - 1; i++) {
			if (first_pokemon[i].getPokemon_Nmae().equals(pk_name)) {
				name2 = first_pokemon[i + 12].getPokemon_Nmae();
				String skill2 = first_pokemon[i + 12].getPokemonSkillName();
				first_pokemon[first_pokemon.length - 1].setPokemon_Nmae(name2);
				first_pokemon[first_pokemon.length - 1].setPokemonSkillName(skill2);
			}
		}
		System.out.println(pk_name + " : 으앗! 몸이 이상..해....");
		pause();
		pause();
		System.out.println("\n");
		im.show(name2);
		System.out.println(first_pokemon[first_pokemon.length - 1].getPokemon_Nmae()+"!!!!!");
	}
	
	
	private static void pause() {
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
}