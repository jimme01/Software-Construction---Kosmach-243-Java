"""Вихідний модуль із побічними ефектами (завдання 1) — лише для порівняння.

У demo.py він не викликається: це «до» рефакторингу.
"""
import time

from .pipeline import parse as try_parse

records = []  # глобальний стан
errors = 0  # глобальний стан


def load(path):
    global errors
    for line in open(path, encoding="utf-8"):  # ефект: відкриття файлу
        print("читаю:", line.rstrip())  # ефект: вивід у консоль
        rec = try_parse(line)
        if rec is None:
            errors += 1  # ефект: зміна глобальної змінної
            continue
        rec["received_at"] = time.time()  # ефект: недетермінованість
        records.append(rec)  # ефект: зміна глобальної змінної
