from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from gensim import models

app = FastAPI()

# model = models.fasttext.load_facebook_model('models/cc.ko.300.bin')
model = models.KeyedVectors.load_word2vec_format('models/cc.ko.300.vec', binary=False, unicode_errors='replace')


class SimilarityRequest(BaseModel):
    word1: str
    word2: str


def calculate_cosine_similarity(word1: str, word2: str) -> float:
    """두 단어의 코사인 유사도를 계산합니다."""
    if word1 not in model or word2 not in model:
        raise ValueError("One or both words are not in the model vocabulary.")

    return model.similarity(word1, word2)


@app.post("/similarity")
def similarity_endpoint(request: SimilarityRequest):
    """POST 요청을 받아 두 단어의 코사인 유사도를 계산하고 결과를 반환합니다."""
    try:
        similarity = calculate_cosine_similarity(request.word1, request.word2)
        return {"similarity": float(similarity)}
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    

########################################################################################################################
# def calculate_cosine_similarity(word1: str, word2: str) -> float:
#     """두 단어의 코사인 유사도를 계산합니다."""
#     if word1 not in model.wv or word2 not in model.wv:
#         raise ValueError("One or both words are not in the model vocabulary.")
#
#     return model.wv.similarity(word1, word2)


# @app.post("/similarity")
# def similarity_endpoint(request: SimilarityRequest):
#     """POST 요청을 받아 두 단어의 코사인 유사도를 계산하고 결과를 반환합니다."""
#     try:
#         similarity = calculate_cosine_similarity(request.word1, request.word2)
#         return {"similarity": float(similarity)}
#     except ValueError as e:
#         raise HTTPException(status_code=404, detail=str(e))


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8000)
