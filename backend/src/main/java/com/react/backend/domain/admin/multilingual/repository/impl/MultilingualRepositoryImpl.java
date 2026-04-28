package com.react.backend.domain.admin.multilingual.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.react.backend.domain.admin.multilingual.dto.SearchMultilingualListResponseDto;
import com.react.backend.domain.admin.multilingual.repository.MultilingualRepositoryCustom;
import com.react.backend.shared.entity.QTLangBase;
import com.react.backend.shared.entity.QTMultilingualBase;
import com.react.backend.shared.entity.QTMultilingualValue;
import com.react.backend.shared.entity.TMultilingualValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class MultilingualRepositoryImpl implements MultilingualRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchMultilingualListResponseDto> searchMultilingualList(
        String langCd, String multilingualKey, String multilingualType, String multilingualVal, Pageable pageable
    ) {
        QTMultilingualBase qtMultilingualBase = QTMultilingualBase.tMultilingualBase;
        QTMultilingualValue qtMultilingualValue = QTMultilingualValue.tMultilingualValue;

        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.hasText(multilingualKey))  where.and(qtMultilingualBase.multilingualKey.contains(multilingualKey));
        if (StringUtils.hasText(multilingualType)) where.and(qtMultilingualBase.multilingualType.eq(multilingualType));
        if (StringUtils.hasText(multilingualVal))  where.and(qtMultilingualValue.multilingualVal.contains(multilingualVal));

        List<SearchMultilingualListResponseDto> content = queryFactory
            .select(qtMultilingualBase, qtMultilingualValue.multilingualVal)
            .from(qtMultilingualBase)
            .leftJoin(qtMultilingualValue).on(
                qtMultilingualValue.id.multilingualKey.eq(qtMultilingualBase.multilingualKey)
                    .and(qtMultilingualValue.id.langCd.eq(langCd))
            )
            .where(where)
            .orderBy(qtMultilingualBase.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(t -> new SearchMultilingualListResponseDto(
                Objects.requireNonNull(t.get(qtMultilingualBase)),
                t.get(qtMultilingualValue.multilingualVal)
            ))
            .toList();

        long total = Objects.requireNonNullElse(
            queryFactory
                .select(qtMultilingualBase.count())
                .from(qtMultilingualBase)
                .leftJoin(qtMultilingualValue).on(
                    qtMultilingualValue.id.multilingualKey.eq(qtMultilingualBase.multilingualKey)
                        .and(qtMultilingualValue.id.langCd.eq(langCd))
                )
                .where(where)
                .fetchOne(),
            0L
        );

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<TMultilingualValue> findAllByMultilingualKey(String multilingualKey) {
        QTMultilingualValue qtMultilingualValue = QTMultilingualValue.tMultilingualValue;
        QTLangBase qtLangBase = QTLangBase.tLangBase;

        return queryFactory
            .selectFrom(qtMultilingualValue)
            .join(qtMultilingualValue.langCd, qtLangBase).fetchJoin()
            .where(qtMultilingualValue.id.multilingualKey.eq(multilingualKey))
            .fetch();
    }

    @Override
    public void deleteAllByMultilingualKey(String multilingualKey) {
        QTMultilingualValue qtMultilingualValue = QTMultilingualValue.tMultilingualValue;

        queryFactory
            .delete(qtMultilingualValue)
            .where(qtMultilingualValue.id.multilingualKey.eq(multilingualKey))
            .execute();
    }
}
