package com.nbafantasy.service;

import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/9/17.
 */
public interface PlayerService {
    CompletionStage<Integer> createPlayerIDFromName(String id, String name);
}
