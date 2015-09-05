# clj-trollol

Read cards from Trello and print summaries.

A Clojure equivalent of [trollol](https://github.com/mieky/trollol), mostly for learning purposes.

## Usage

With [leiningen](http://leiningen.org/) installed:

```
cp cards.debug.json.example cards.debug.json
cp config.json.example config.json
LOCAL=yessir KEY=my_key TOKEN=my_token BOARD=my_board lein run
```

## TODO

- [ ] make deployable to Heroku
- [ ] set up Travis build

## Acknowledgements

This project is a grateful recipient of the [Futurice Open Source sponsorship program](http://futurice.com/blog/sponsoring-free-time-open-source-activities).

## License

[MIT](https://github.com/mieky/clj-trollol/blob/master/LICENSE)
