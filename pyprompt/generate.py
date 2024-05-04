import os
import csv

# 영문 주어 배열
eng_subjects = [
    "A young woman", "A young artist", "A skilled chef", "An elderly gardener", "A curious child",
    "A creative writer", "A diligent student", "An office lady", "A professional musician", "A handsome man"
]

# 한글 주어 배열
kor_subjects = [
    "젊은 여성", "젊은 예술가", "숙련된 요리사", "노인 정원사", "호기심 많은 아이",
    "창의적인 작가", "성실한 학생", "사무직 여성", "전문 음악가", "잘생긴 남성"
]

# 영문 동사 배열
eng_verbs = [
    "is experiencing", "is thinking", "is creating", "is observing", "is imagining",
    "is describing", "is photographing", "is looking", "is painting", "is writing",
    # "is appreciating"
]

# 한글 동사 배열
kor_verbs = [
    "경험하고 있다", "생각하고 있다", "창조하고 있다", "관찰하고 있다", "상상하고 있다",
    "설명하고 있다", "사진찍고 있다", "보고 있다", "그리고 있다", "작성하고 있다",
    # "감상하고 있다",
]

# 영문 형용사 배열
eng_adjectives = [
    "beautiful", "old", "wonderful", "terrible", "sad",
    "mysterious", "important", "interesting", "boring", "memorable"
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

# 한글 형용사 배열
kor_adjectives = [
    "아름다운", "오래된", "멋진", "끔찍한", "슬픈",
    "신비로운", "중요한", "흥미로운", "지루한", "기억에 남는"
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

# 영문 부사 배열
eng_adverbs = [
    "in the bustling city center", "in a busy restaurant kitchen", "in the serene garden",
    "at a quiet library corner", "in a busy office", "in a quaint coffee shop", "in the vast fields",
    "on a hot air balloon", "inside the old bookstore", "at the summit of a snowy mountain",
]

# 한글 부사 배열
kor_adverbs = [
    "번화한 도시 중심에서", "바쁜 레스토랑 주방에서", "고요한 정원에서",
    "조용한 도서관 구석에서", "바쁜 사무실에서", "이국적인 커피숍에서", "넓은 들판에서",
    "열기구 안에서", "고서점 안에서", "눈 덮인 산의 정상에서",
]

# 영문 목적어 배열
eng_objects = [
    "scenery", "moment", "masterpiece", "someone", "music",
    "meal", "book", "aircraft", "story", "fire",
    # "castle", "forest", "garden", "painting", "sculpture",
]

# 한글 목적어 배열
kor_objects = [
    "풍경", "순간", "걸작", "누군가", "음악",
    "식사", "책", "항공기", "이야기", "불",
    # "성", "숲", "정원", "그림", "조각상",
]


def generate_sentence():
    sentences = []
    category_id = 0
    for eng_subject, kor_subject in zip(eng_subjects, kor_subjects):
        for eng_object, kor_object in zip(eng_objects, kor_objects):
            for eng_verb, kor_verb in zip(eng_verbs, kor_verbs):
                category_id += 1
                for eng_adjective, kor_adjective in zip(eng_adjectives, kor_adjectives):
                    for eng_adverb, kor_adverb in zip(eng_adverbs, kor_adverbs):
                        for style in ('anime', 'cartoon', 'realistic'):
                            eng_sentence = f'{eng_subject} {eng_verb} {eng_adjective} {eng_object} {eng_adverb}'
                            kor_sentence = f'{kor_subject}이(가) {kor_adjective} {kor_object}을(를) {kor_adverb} {kor_verb}'
                            sentences.append([
                                eng_sentence, eng_subject, eng_verb, eng_object, eng_adjective, eng_adverb,
                                kor_sentence, kor_subject, kor_verb, kor_object, kor_adjective, kor_adverb,
                                category_id, style
                            ])
                            print(f'Category: {category_id} | Style: {style} | Eng Sentence: {eng_sentence}')
                            print(f'Category: {category_id} | Style: {style} | Kor Sentence: {kor_sentence}')
                        print()
    return sentences


def save_as_csv(sentences):
    # CSV 파일로 저장
    os.makedirs('datasets', exist_ok=True)
    with open('datasets/sentences1.csv', 'w', newline='', encoding='cp949') as file:
        writer = csv.writer(file)
        writer.writerow([
            "eng_sentence", "eng_subject", "eng_verb", "eng_object", "eng_adjective", "eng_adverb",
            "kor_sentence", "kor_subject", "kor_verb", "kor_object", "kor_adjective", "kor_adverb",
            "group_code", "style"
        ])
        writer.writerows(sentences)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    sentences = generate_sentence()
    save_as_csv(sentences)
