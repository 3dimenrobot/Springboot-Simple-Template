export function currentRouteArea(route) {
  if (route.path.indexOf("/corn-")< 0) return {}
  let matched = route.matched.filter(item => item.meta && item.meta.title)
  if (matched.length != 2) return {}
  return {
    region: matched[0].meta.title,
    province:matched[1].meta.title
  }
}

