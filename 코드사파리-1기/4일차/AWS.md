# 서버 구매
## AWS EC2 서버 구매하기
Linux OS의 컴퓨터를 사서 서버로 사용하겠습니다. 오픈소스 운영체제로, 우리 주변 대부분의 서비스가 Linux OS로 서버를 운영하고 있습니다.

https://ap-northeast-2.console.aws.amazon.com/ec2/v2/home?region=ap-northeast-2

AWS 검색창에서 EC2를 검색해서 들어갑니다. 오른쪽 즐겨찾기(별표)를 등록할 수 있어요.

![image](https://github.com/user-attachments/assets/6d14ee7f-85f7-491b-b775-a1a540eea464)

대시보드 화면이 보일 거예요. 여기에서 인스턴스 시작을 눌러주세요. 인스턴스는 컴퓨터 하나라고 생각하시면 돼요. 인스턴스 시작은 AWS 클라우드 가상 컴퓨터 한 대를 켠다는 의미입니다

![image](https://github.com/user-attachments/assets/80d103e2-c90a-40eb-8604-9c4e0362262a)

인스턴스의 이름을 설정해 주세요. 애플리케이션 및 OS 이미지는 Quick Start의 Ubuntu로 설정해 주세요.

Amazon Machine Image(AMI)는 Ubuntu Server 24.04 LTS로 설정해 주세요.

![image](https://github.com/user-attachments/assets/10319cf6-685c-44ae-9f27-72f679d74bb8)

인스턴스 유형은 t2.micro로 설정해 주세요. 사양은 낮지만 우리의 웹 서버로는 충분해요! t2.micro는 프리 티어로 사용 가능합니다.

![image](https://github.com/user-attachments/assets/825aa6db-7835-4f74-a8d8-bcc4699ae48e)

새 키 페어 생성을 눌러 키 페어를 만들어 주세요. 키 페어 이름은 찾기 쉽게 해주세요. (예: my-server-key) 생성된 키 페어 파일은 찾기 쉬운 곳에 잘 보관해 주세요. 키 페어는 내 컴퓨터 터미널에서 EC2 인스턴스에 연결하는 데 사용됩니다.

키 페어 유형은 RSA, 형식은 .pem 으로 설정하세요. my-server-key.pem 파일이 만들어 집니다.

![image](https://github.com/user-attachments/assets/b8994f55-eaa4-489c-8524-fd5e5e87fd78)

EC2 인스턴스에서 Flask 웹 서버를 실행하고 외부에서 HTTP로 접속할 수 있도록 네트워크 설정을 구성하겠습니다. 보안 그룹 생성 에서 SSH, HTTPS, HTTP 트래픽을 모두 허용해 주세요. 설정이 완료되었다면 인스턴스 시작을 눌러 인스턴스를 생성합니다.

![image](https://github.com/user-attachments/assets/dbec50fe-3e0f-4222-9ddf-c153f35865d4)

EC2 서버 종료하는 방법 (1년 후 자동 결제 방지)
 
무료 기간(1년) 후 결제가 되기 전에, 이렇게 종료하세요. 대상 인스턴스에 마우스 우클릭 → 인스턴스 종료(삭제)를 클릭하면 인스턴스를 완전히 삭제할 수 있습니다. 인스턴스 중지는 컴퓨터를 잠시 꺼놓은 것이고, 삭제는 컴퓨터를 버리는 것으로 이해해 주시면 됩니다.

## AWS EC2 접속 준비
git bash를 실행하고, 아래를 입력!
```
ssh -i 받은키페어를끌어다놓기 ubuntu@AWS에적힌내아이피
```

예시
```
ssh -i /c/Users/skcy1/OneDrive/Desktop/skcy151515.pem ubuntu@13.124.230.229
```

git bash를 종료할 때는 exit 명령어를 입력하여 ssh 접속을 먼저 끊어주세요.

# 서버 설정
## 리눅스 명령어
```
ls: 내 위치의 모든 파일을 보여준다.

pwd: 내 위치(폴더의 경로)를 알려준다.

mkdir: 내 위치 아래에 폴더를 하나 만든다.

cd [갈 곳]: 나를 [갈 곳] 폴더로 이동시킨다.

cd .. : 나를 상위 폴더로 이동시킨다.

cp -r [복사할 것] [붙여넣기 할 것]: 복사 붙여넣기

rm -rf [지울 것]: 지우기

sudo [실행 할 명령어]: 명령어를 관리자 권한으로 실행한다.
sudo su: 관리가 권한으로 들어간다. (나올때는 exit으로 나옴)
```

## 파일질라 이용하기
우리 EC2에 파일 업로드해 보겠습니다.

https://filezilla-project.org/download.php

![image](https://github.com/user-attachments/assets/5435ef0c-e266-4ea5-aec6-04dfcf783e57)

기본 버전 (스크린샷 기준 왼쪽) 다운로드!

![image](https://github.com/user-attachments/assets/465733e2-ee30-4404-b2d6-9fd324242be8)

파일 -> 사이트 관리자 -> 새 사이트 누르고  

프로토콜은 SFTP, 호스트는 AWS 퍼블릭 IPv4 주소, 포트는 22 로 설정해 주세요.

로그온 유형은 키 파일 , 사용자는 ubuntu, 키 파일은 찾아보기를 통해 발급받았던 pem 파일을 찾아 선택하고 연결

![image](https://github.com/user-attachments/assets/47318e50-ac62-449a-96dc-d6811472140c)

마우스로 드래그해서 파일을 업로드/다운로드할 수 있습니다. 기본 설정은 왼쪽이 내 컴퓨터, 오른쪽이 원격접속한 EC2 인스턴스입니다.

### 파이썬 파일 실행
![image](https://github.com/user-attachments/assets/ee34e899-09fd-4aec-87fe-26a229ef217d)

간단한 파이썬 파일을 만들고 오른쪽 칸에 드래그

git bash에 아래 코드 입력
```
# python 이라는 명령어로 3 버전 이상을 실행하도록 하는 명령어입니다.
sudo update-alternatives --install /usr/bin/python python /usr/bin/python3 10

# home 디렉토리로 이동
cd ~

# 실행. 콘솔창에 hellow world!가 뜨는 것을 확인 할 수 있습니다.
python hello.py
```

![image](https://github.com/user-attachments/assets/db6d448d-3e35-473b-88e1-d3e00ff8c622)

# 서버 실행하기
## Flask 서버 실행해보기
```
# pip3 설치
sudo apt-get update
sudo apt-get install -y python3-pip

# 버전 확인
pip3 --version

# pip3 대신 pip 라고 입력하기 위한 명령어
# 아래 명령어를 입력하면 pip 라고 쳐도 pip3를 작동시킬 수 있습니다.
sudo update-alternatives --install /usr/bin/pip pip /usr/bin/pip3 1
```
패키지 설치를 도와줄 패키지, pip 설치하기

```
python3 -m venv myenv
source myenv/bin/activate #가상환경 활성화
pip install flask

deactivate # 가상환경 끄기
```
flask 설치 (가상환경 이용)

## AWS에서 5000, 27017 포트를 열어주기
포트(port)란? 포트는 컴퓨터가 서로 다른 작업을 구분해서 처리하기 위해 사용하는 통신 채널 입니다. 각각의 포트는 특정한 역할을 맡습니다.

HTTP는 80번, SSH는 22번, MongoDB는 27017번, 우리가 만든 플라스크 서버는 5000번입니다.

- EC2에서 5000포트로 들어오는 요청을 받을 수 있게 설정해야 합니다. AWS EC2 Security Group(보안 그룹)에서 요청 포트를 열어주면 됩니다.
- 일단, EC2 관리 콘솔로 들어갑니다. 그리고 보안(Security) > 보안 그룹(Security Group)을 눌러 들어갑니다. 여기선 launch-wizard-1이라고 쓰여 있네요

![image](https://github.com/user-attachments/assets/ec7b7494-6525-49bb-9257-7ec8bb8ae1fb)

해당 보안그룹을 클릭하고 인바운드 규칙 편집을 누릅니다.
 
EC2의 보안 그룹은 EC2 서버로 들어오거나 나가는 IP 주소와 포트를 관리하는 역할을 해요.

인바운드 규칙은 “EC2 서버로 들어오는 길을 열어주는 규칙”이라고 생각하면 돼요.

![image](https://github.com/user-attachments/assets/7c56cfc5-df0d-4111-85e1-a3ff231f6bc7)

세 가지 포트를 추가해 봅니다. (80, 5000포트는 미리 추가해두겠습니다) 소스는 Anywhere-IPv4로 해주세요
- 5000포트: Flask 기본포트
- 80포트: HTTP 접속을 위한 기본포트
- 27017포트: 외부에서 MongoDB 접속하기 위한 포트

![image](https://github.com/user-attachments/assets/95ac92a0-0099-42c6-a48d-5a3615f64e1f)

```
http://[내 EC2 퍼블릭 IP]:5000/
```

```
http://13.124.230.229:5000/
```
![image](https://github.com/user-attachments/assets/940e949e-5d72-4b6d-8160-e33ffa955bf5)

접속하면 화면이 뜬다.

# 서버에 나홀로 링크 메모장 업로드
