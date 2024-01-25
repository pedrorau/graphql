package com.pedrorau.graphqlx.data

import com.apollographql.apollo3.ApolloClient
import com.pedrorau.CountriesQuery
import com.pedrorau.CountryQuery
import com.pedrorau.graphqlx.domain.CountryClient
import com.pedrorau.graphqlx.domain.DetailedCountry
import com.pedrorau.graphqlx.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
) : CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}
