import os
import pandas as pd

SAVE_PATH = 'datasets'
CSV_FILENAME = 'sentences1.csv'
SQL_FILENAME = 'sentences1.sql'
DATABASE_TABLE_NAME = 'prompt'


def convert_csv_to_sql():
    df = pd.read_csv(f'{SAVE_PATH}/{CSV_FILENAME}', encoding='cp949')

    if not os.path.exists(SAVE_PATH):
        os.makedirs(SAVE_PATH)

    with open(f'{SAVE_PATH}/{SQL_FILENAME}', 'w', encoding='utf-8') as file:
        for index, row in df.iterrows():
            sql = f"INSERT INTO {DATABASE_TABLE_NAME} ({', '.join(row.index)}) VALUES "
            sql += "(" + ', '.join([f"'{str(item).replace("'", "''")}'" for item in row.values]) + ");\n"
            file.write(sql)


if __name__ == '__main__':
    convert_csv_to_sql()
    print('Convert CSV to SQL Complete Successfully')
