package com.grapheople.miner.config;

enum PhaseType {
    none,
    test,			// 테스트 케이스 수행
    bmt,			// 성능 테스트

    local,			// 로컬 개발용
    dev, 			// 개발 단계, 내부망
    sandbox, 		// 외부에서 접근 가능, QA
    cbt,			// 실서비스 데이터, 베타 전용 web, api 서버
    production 	    // release (실서비스 데이터)
}
