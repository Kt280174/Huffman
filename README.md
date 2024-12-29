# Huffman
## Формат данных во входном файл:
### Закодированный текст:
####Первый байт: Количество значащих битов в последнем байте.
####Последующие байты: Закодированные данные в виде двоичной строки.
####Пример:
Двоичная строка кодирования: 10111000110.
Первый байт: 3 (только 3 значащих бита в последнем байте).
10111000 (первый байт)
11000000 (второй байт, 3 значащих бита, 5 заполнены нулями)
### Словарь:
Каждый символ (байт) сохраняется вместе с его длиной кода и его двоичным кодом.
Например:
Для символа A с кодом 101, данные будут сохранены как:
65 (ASCII-код 'A') | 3 (длина кода) | 5 (значение двоичного числа 101)
