# DragonFlight copy TODOs

## Game TODO

* Game Skeleton
  * SampleGame 으로부터 재활용 가능한 것들을 가져온 뒤 컴파일 에러 없애기
  * ActionBar 없애기
  * 세로모드만 지원하기
* Fighter 는 y 좌표가 고정되며 좌우로만 움직임
* 총알 발사
  * 화면 밖으로 나간 총알은 삭제
  * 주기적으로 자동 발사
* Enemy 생성
  * EnemyGenerator 를 이용하여 생성
  * Enemy Level 을 1~20 으로 한다
  * 랜덤하지만 차차 레벨이 올라갈 수 있는 수식을 만든다

## Framework TODO

* Object 갯수 출력
* Object 를 삭제할 수 있도록
* Sprite 에 Animation 지원
* 충돌 체크 지원 (Box to Box)
* Object Pool 지원
* Activity Life-cycle 연동
  * Pause
  * Resume
  * Destory
