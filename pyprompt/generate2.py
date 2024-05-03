import os
import csv

# 영문 주어 리스트
eng_subjects = [
    'man', 'woman', 'boy', 'girl', 'dog', 'cat',
    'police officer', 'teacher', 'doctor', 'nurse',

    # "A young woman", "A young artist", "A skilled chef", "An elderly gardener", "A curious child",
    # "A creative writer", "A diligent student", "An office lady", "A professional musician", "A handsome man"
]

# 한글 주어 리스트
kor_subjects = [
    '남자', '여자', '남자아이', '여자아이', '개', '고양이',
    '경찰관', '선생님', '의사', '간호사',

    # "젊은 여성", "젊은 예술가", "숙련된 요리사", "노인 정원사", "호기심 많은 아이",
    # "창의적인 작가", "성실한 학생", "사무직 여성", "전문 음악가", "잘생긴 남성"
]

########################################################################################################################
# 영문 주어 형용사 리스트
eng_sub_adjectives = [
    'young', 'old', 'beautiful', 'short_hair', 'long_hair',
    # 'ugly', 'exemplary', 'sexy',

    
    # "beautiful", "old", "wonderful", "terrible", "sad",
    # "mysterious", "important", "interesting", "boring", "memorable"
    # "expensive", "cheap", "happy",  "good", "bad", "nice", "horrible",
    # "fantastic", "great", "large", "tiny", "enormous", "short", "long", "bright",
    # "dark", "hot", "cold", "warm", "cool", "noisy", "quiet", "soft", "hard",
    # "heavy", "light", "rich", "poor", "clean", "dirty", "fresh", "stale", "strong",

    # imaginative, thorough, graceful, brave, efficient,
    # artistic, expert, careful, intent, skillful,
    # beautiful, intriguing, impressive, fun, harmonious,
    # important, useful, unforgettable, natural, necessary,
    # elegant, professional, deliberate, interesting,
]

# 한글 주어 형용사 리스트
kor_sub_adjectives = [
    '젊은', '늙은', '아름다운', '짧은 머리의', '긴 머리의',
    # '못생긴', '모범적인', '섹시한',

    # "아름다운", "오래된", "멋진", "끔찍한", "슬픈",
    # "신비로운", "중요한", "흥미로운", "지루한", "기억에 남는"
    # "비싼", "싼", "행복한", , "좋은", "나쁜", "멋진", "무서운",
    # "환상적인", "훌륭한", "큰", "작은", "거대한", "짧은", "긴", "밝은",
    # "어두운", "더운", "추운", "따뜻한", "시원한", "시끄러운", "조용한", "부드러운", "딱딱한",
    # "무거운", "가벼운", "부유한", "가난한", "깨끗한", "더러운", "신선한", "질긴", "강한",

    # 상상력이 풍부한, 철저한, 우아한, 용감한, 효율적인,
    # 예술적인, 전문적인, 신중한, 의도적인, 능숙한,
    # 아름다운, 흥미로운, 인상적인, 재미있는, 조화로운,
    # 중요한, 유용한, 잊을 수 없는, 자연스러운, 필요한,
    # 우아한, 전문적인, 신중한, 흥미로운
]

########################################################################################################################
# # 영문 목적어 리스트
# eng_objects = [
#
#     "scenery", "moment", "masterpiece", "someone", "music",
#     "meal", "book", "aircraft", "story", "fire",
#     # "castle", "forest", "garden", "painting", "sculpture",
# ]
#
# # 한글 목적어 리스트
# kor_objects = [
#
#     "풍경", "순간", "걸작", "누군가", "음악",
#     "식사", "책", "항공기", "이야기", "불",
#     # "성", "숲", "정원", "그림", "조각상",
# ]

########################################################################################################################
# # 영문 동사 리스트
# eng_verbs = [
#     "is experiencing", "is thinking", "is creating", "is observing", "is imagining",
#     "is describing", "is photographing", "is looking", "is painting", "is writing",
#     # "is appreciating"
# ]
#
# # 한글 동사 리스트
# kor_verbs = [
#     "경험하고 있다", "생각하고 있다", "창조하고 있다", "관찰하고 있다", "상상하고 있다",
#     "설명하고 있다", "사진찍고 있다", "보고 있다", "그리고 있다", "작성하고 있다",
#     # "감상하고 있다",
# ]



########################################################################################################################
# 영문 목적어 리스트
eng_objects = [
    'water', 'window', 'book', 'bicycle', 'music', 'sea', 'coffee', 'shopping', 'exercise', 'room',
    'fish', 'cooking', 'walk', 'phone', 'tennis', 'desk', 'game', 'song', 'plan', 'mountain',
    'sunlight', 'sleep', 'photo', 'delicious food', 'writing', 'swimming', 'walk', 'pencil case', 'love', 'computer',
    'hat', 'television', 'birdsong', 'invitation', 'art', 'pet', 'sports', 'sunlight', 'sky', 'guitar',
    'handwritten letter', 'meeting', 'river', 'laptop', 'curtain', 'piano', 'park', 'trash', 'plant', 'bat',
    # 'driving', 'reading', 'plant', 'children', 'bathroom', 'time', 'plant', 'concert', 'exercise', 'window',
    # 'coffee', 'meal', 'telephone', 'mirror', 'paper', 'book', 'homework', 'gift', 'movie', 'egg',
    # 'transport', 'thinking', 'book'
]

# 한글 목적어 리스트
kor_objects = [
    '물', '창문', '책', '자전거', '음악', '바다', '커피', '쇼핑', '운동', '방',
    '물고기', '요리', '산책', '휴대폰', '테니스', '책상', '게임', '노래', '계획', '산',
    '햇빛', '잠', '사진', '맛있는 음식', '글', '수영', '산책', '필통', '사랑', '컴퓨터',
    '모자', '텔레비전', '새소리', '초대장', '예술', '반려견', '스포츠', '햇볕', '하늘', '기타',
    '손편지', '회의', '강', '노트북', '커튼', '피아노', '공원', '쓰레기', '화분', '배트',
    # '운전', '독서', '화분', '어린이', '화장실', '시간', '식물', '연주회', '운동', '창문',
    # '커피', '식사', '전화', '거울', '종이', '책', '숙제', '선물', '영화', '계란',
    # '교통', '생각', '책'
]

########################################################################################################################
# 영문 목적어 형용사 리스트
eng_obj_adjectives = [
    "small", "huge", "colorful", "smooth", "shiny",
    # "new", "old", "clean", "dirty",
]

# 한글 목적어 형용사 리스트
kor_obj_adjectives = [
    "작은", "거대한", "다채로운", "부드러운", "빛나는",
    # "새로운", "오래된", "꺠끗한", "더러운",
]

########################################################################################################################
# 영문 동사 리스트
eng_verbs = [
    'drinking', 'closing', 'reading', 'riding', 'listening', 'watching', 'drinking', 'shopping', 'exercising', 'cleaning',
    'observing', 'cooking', 'walking', 'watching', 'playing', 'tidying', 'playing', 'singing', 'planning', 'climbing',
    'enjoying', 'sleeping', 'taking', 'eating', 'writing', 'swimming', 'departing', 'tidying', 'sharing', 'fixing',
    'wearing', 'watching', 'listening', 'viewing', 'creating', 'playing', 'observing', 'enjoying', 'watching', 'playing',
    'writing', 'preparing', 'crossing', 'using', 'drawing', 'playing', 'walking', 'throwing away', 'tending', 'hitting',
    # 'driving', 'reading', 'watering', 'caring for', 'cleaning', 'spending', 'growing', 'attending', 'teaching', 'opening',
    # 'making', 'preparing', 'receiving', 'looking at', 'folding', 'writing', 'doing', 'wrapping', 'watching', 'scrambling',
    # 'riding', 'thinking', 'lending'
]

# 한글 동사 리스트
kor_verbs = [
    '마시고 있다', '닫고 있다', '읽고 있다', '타고 있다', '듣고 있다', '보고 있다', '마시고 있다', '하고 있다', '하고 있다', '정리하고 있다',
    '관찰하고 있다', '하고 있다', '하고 있다', '보고 있다', '치고 있다', '정리하고 있다', '하고 있다', '부르고 있다', '세우고 있다', '오르고 있다',
    '즐기고 있다', '자고 있다', '찍고 있다', '먹고 있다', '쓰고 있다', '하고 있다', '떠나고 있다', '정리하고 있다', '나누고 있다', '고치고 있다',
    '쓰고 있다', '보고 있다', '듣고 있다', '보고 있다', '만들고 있다', '놀고 있다', '관찰하고 있다', '즐기고 있다', '바라보고 있다', '연주하고 있다',
    '쓰고 있다', '준비하고 있다', '건너고 있다', '사용하고 있다', '걷고 있다', '연주하고 있다', '산책하고 있다', '버리고 있다', '관리하고 있다', '치고 있다',
    # '하고 있다', '하고 있다', '물주고 있다', '돌보고 있다', '청소하고 있다', '보내고 있다', '키우고 있다', '관람하고 있다', '가르치고 있다', '열고 있다',
    # '만들고 있다', '준비하고 있다', '받고 있다', '보고 있다', '접고 있다', '쓰고 있다', '하고 있다', '포장하고 있다', '보고 있다', '풀고 있다',
    # '타고 있다', '하고 있다', '빌려주고 있다'
]










# # 영문 부사 배열
# eng_adverbs = [
#     "in the bustling city center", "in a busy restaurant kitchen", "in the serene garden",
#     "at a quiet library corner", "in a busy office", "in a quaint coffee shop", "in the vast fields",
#     "on a hot air balloon", "inside the old bookstore", "at the summit of a snowy mountain",
# ]
#
# # 한글 부사 배열
# kor_adverbs = [
#     "번화한 도시 중심에서", "바쁜 레스토랑 주방에서", "고요한 정원에서",
#     "조용한 도서관 구석에서", "바쁜 사무실에서", "이국적인 커피숍에서", "넓은 들판에서",
#     "열기구 안에서", "고서점 안에서", "눈 덮인 산의 정상에서",
# ]




def generate_sentence():
    sentences = []
    category_id = 0
    for eng_subject, kor_subject in zip(eng_subjects, kor_subjects):    # 10개 주어
        for eng_object, kor_object, eng_verb, kor_verb in zip(eng_objects, kor_objects, eng_verbs, kor_verbs):  # 50개 목적어 동사
            category_id += 1
            for eng_sub_adjective, kor_sub_adjective in zip(eng_sub_adjectives, kor_sub_adjectives):        # 5개 주어 형용사
                for eng_obj_adjective, kor_obj_adjective in zip(eng_obj_adjectives, kor_obj_adjectives):    # 5개 목적어 형용사
                    for style in ('anime', 'cartoon', 'realistic'):     # 3개 스타일
                        indefinite_article = 'An' if eng_sub_adjective[0] in ['a', 'e', 'i', 'o', 'u'] else 'A'
                        eng_sentence = f'{indefinite_article} {eng_sub_adjective} is {eng_subject} {eng_verb} {eng_obj_adjective} {eng_object}.'
                        kor_sentence = f'{kor_sub_adjective} {kor_subject}이(가) {kor_obj_adjective} {kor_object}을(를) {kor_verb}.'
                        sentences.append([
                            eng_sentence, eng_subject, eng_verb, eng_object, eng_sub_adjective, eng_obj_adjective,
                            kor_sentence, kor_subject, kor_verb, kor_object, kor_sub_adjective, kor_obj_adjective,
                            category_id, style
                        ])
                        # print(f'Category: {category_id} | Style: {style} | Eng Sentence: {eng_sentence}')
                        # print(f'Category: {category_id} | Style: {style} | Kor Sentence: {kor_sentence}')
                    # print(f'Category: {category_id} | Eng Sentence: {eng_sentence}')
                    print(f'Category: {category_id} | Kor Sentence: {kor_sentence}')
            print()
    print(len(sentences))
    return sentences


def save_as_csv(sentences):
    # CSV 파일로 저장
    os.makedirs('datasets', exist_ok=True)
    with open('datasets/sentences2.csv', 'w', newline='', encoding='cp949') as file:
        writer = csv.writer(file)
        writer.writerow([
            "eng_sentence", "eng_subject", "eng_verb", "eng_object", "eng_sub_adjective", "eng_obj_adjective",
            "kor_sentence", "kor_subject", "kor_verb", "kor_object", "kor_sub_adjective", "kor_obj_adjective",
            "group_code", "style"
        ])
        writer.writerows(sentences)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    sentences = generate_sentence()
    # save_as_csv(sentences)
