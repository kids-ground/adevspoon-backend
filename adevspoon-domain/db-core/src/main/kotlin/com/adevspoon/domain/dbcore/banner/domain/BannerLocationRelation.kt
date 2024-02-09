package com.adevspoon.domain.dbcore.banner.domain

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable

@Entity
@Table(name = "banner_location_relation", schema = "adevspoon")
class BannerLocationRelation (
    @EmbeddedId
    val bannerLocationRelationId: BannerLocationRelationId? = null
)

@Embeddable
class BannerLocationRelationId(
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "banner_id")
    val banner: Banner? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    val location: BannerLocation? = null
) : Serializable