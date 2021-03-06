package com.peyto.aiengine.example.ai_cup_4;

import com.peyto.aiengine.example.ai_cup_4.model.Car;
import com.peyto.aiengine.example.ai_cup_4.model.Game;
import com.peyto.aiengine.example.ai_cup_4.model.Move;
import com.peyto.aiengine.example.ai_cup_4.model.World;

/**
 * Стратегия --- интерфейс, содержащий описание методов искусственного интеллекта кодемобиля.
 * Каждая пользовательская стратегия должна реализовывать этот интерфейс.
 * Может отсутствовать в некоторых языковых пакетах, если язык не поддерживает интерфейсы.
 */
public interface Strategy {
    /**
     * Основной метод стратегии, осуществляющий управление кодемобилем.
     * Вызывается каждый тик для каждого кодемобиля.
     *
     * @param self  Кодемобиль, которым данный метод будет осуществлять управление.
     * @param world Текущее состояние мира.
     * @param game  Различные игровые константы.
     * @param move  Результатом работы метода является изменение полей данного объекта.
     */
    void move(Car self, World world, Game game, Move move);
}
