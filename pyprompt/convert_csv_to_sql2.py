import os
import pandas as pd

# 3개 style * 100개 프롬프트씩 * 100 그룹씩 Batch 처리
BATCH_SIZE = 30000
SAVE_PATH = 'datasets'
CSV_FILENAME = 'sentences2.csv'
SQL_FILENAME = 'sentences2.sql'
DATABASE_TABLE_NAME = 'prompt'


def convert_csv_to_sql():
    df = pd.read_csv(f'{SAVE_PATH}/{CSV_FILENAME}', encoding='cp949')

    if not os.path.exists(SAVE_PATH):
        os.makedirs(SAVE_PATH)

    with open(f'{SAVE_PATH}/{SQL_FILENAME}', 'w', encoding='utf-8') as file:
        i = 0
        columns = ', '.join(df.columns)
        while i < len(df):
            batch = []
            sql = f'INSERT INTO {DATABASE_TABLE_NAME} ({columns}) VALUES\n'
            for index, row in df.iloc[i:i + BATCH_SIZE].iterrows():
                values = ', '.join([f'\'{str(item)}\'' for item in row])
                batch.append(f'({values})')
            sql += ',\n'.join(batch) + ';\n'
            file.write(sql)
            i += BATCH_SIZE


if __name__ == '__main__':
    convert_csv_to_sql()
    print('Convert CSV to SQL Complete Successfully')
