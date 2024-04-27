import random
import pandas as pd
from googletrans import Translator

# 사전 정의된 단어 리스트
subjects = [
    "The student", "The cat", "The entrepreneur", "The observer", "The driver",
    "The dog", "The professor", "The runner", "The artist", "The child"
]

objects = [
    "problem", "yarn", "venture", "event", "road",
    "book", "machine", "treasure", "landscape", "mountain"
]

verbs = [
    "examines", "plays with", "starts", "observes", "navigates",
    "chases", "teaches", "runs through", "depicts", "climbs"
]

adjectives = [
    "complex", "colorful", "new", "interesting", "slippery",
    "old", "broken", "hidden", "digital", "high"
]

adverbs = [
    "carefully", "playfully", "proactively", "attentively", "skillfully",
    "patiently", "methodically", "quickly", "artistically", "eagerly"
]

# 데이터 생성
eng_data = []
# kor_data = []
category_id = 1
translator = Translator()

for subject in subjects:
    for object in objects:
        for verb in verbs:
            for adjective in adjectives:
                for adverb in adverbs:
                    # 형용사와 명사를 조합하여 'a/an'을 적절히 사용
                    article = 'an' if adjective[0] in 'aeiou' else 'a'
                    sentence = f'{subject} {adverb} {verb} {article} {adjective} {object}.'

                    # kor_subject = translator.translate(subject, dest='ko').text
                    # kor_object = translator.translate(object, dest='ko').text
                    # kor_verb = translator.translate(verb, dest='ko').text
                    # kor_adjective = translator.translate(adjective, dest='ko').text
                    # kor_adverb = translator.translate(adverb, dest='ko').text
                    # kor_sentence = translator.translate(sentence, dest='ko').text

                    eng_data.append([sentence, subject, object, verb, adjective, adverb, category_id])
                    # kor_data.append([kor_sentence, kor_subject, kor_object, kor_verb, kor_adjective, kor_adverb, category_id])
                    # print(subject, object, verb, adjective, adverb, category_id, sentence)
                    # print(kor_subject, kor_object, kor_verb, kor_adjective, kor_adverb, category_id, kor_sentence)
            category_id += 1

# 데이터 프레임 생성
df1 = pd.DataFrame(eng_data, columns=["sentence", "subject", "object", "verb", "adjective", "adverb", "category_id"])
# df2 = pd.DataFrame(kor_data, columns=["sentence", "subject", "object", "verb", "adjective", "adverb", "category_id"])

# 영어 CSV 파일 저장
df1.to_csv("english_sentences4.csv", index=False)
# df2.to_csv("korean_sentences4.csv", index=False)