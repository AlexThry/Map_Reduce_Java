# Todo
- peut etre changer la hashmap avec une liste : on ajoute chaque mot dans une liste sans les compter *ok*
- Il faut créer les réducers avant les mappers. *ok*
- trouver une fonction de hash → Sha64 ou plus petite *ok* → classe SimpleHash
- nombre de possibilités de hash divisées par le nombre de reducer *ok*
- En créant les mappers, on leur donne la liste des reducers *ok*
- quand les reducers ont leur mots, on multithread
- Trouver un moyen pour le shuffle de tous les threads. Dans le mapper ou preprocesseur ?