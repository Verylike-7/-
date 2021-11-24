package com.hema.hmfresh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hema.hmfresh.mapper.ScoreMapper;
import com.hema.hmfresh.pojo.Score;
import com.hema.hmfresh.service.ScoreService;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
}
